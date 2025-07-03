package sdv.devduo.yukool.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.ProduitRaw;
import sdv.devduo.yukool.model.*;
import sdv.devduo.yukool.repository.*;
import sdv.devduo.yukool.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
public class CsvImportService {


    /** */
    private final MarqueRepository marqueRepository;
    /** */
    private final CategorieRepository categorieRepository;
    /** */
    private final IngredientRepository ingredientRepository;
    /** */
    private final AdditifRepository additifRepository;
    /** */
    private final AllergeneRepository allergeneRepository;
    /** */
    private final ProduitRepository produitRepository;

    private List<ProduitRaw> produitRaws = new ArrayList<>();

    /** Index => liste de caractères à nettoyer/remplacer*/
    private Map<Integer, List<Character>> columnSeparators = Map.of(
            0, List.of(','),
            1, List.of(','),
            4, List.of(',',';','-'),
            28, List.of(','),
            29, List.of(',')
    );

    @Autowired
    public CsvImportService(MarqueRepository marqueRepository, CategorieRepository categorieRepository, IngredientRepository ingredientRepository, AdditifRepository additifRepository, AllergeneRepository allergeneRepository, ProduitRepository produitRepository) {
        this.marqueRepository = marqueRepository;
        this.categorieRepository = categorieRepository;
        this.ingredientRepository = ingredientRepository;
        this.additifRepository = additifRepository;
        this.allergeneRepository = allergeneRepository;
        this.produitRepository = produitRepository;
    }

    /**
     *
     * @param csvPath
     * @throws Exception
     */
    public void importCsv(String csvPath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String header = br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                produitRaws.add(parseLineToProductRaw(line));
            }
            transformAndPersist();
        }
    }

    /**
     *
     * @param line
     * @return
     */
    private ProduitRaw parseLineToProductRaw(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 30) return null;
        for (int i = 0; i < parts.length; i++) {
            List<Character> separators = columnSeparators.getOrDefault(i, List.of());
            parts[i] = StringUtils.cleanString(parts[i], separators);
        }
        ProduitRaw raw = new ProduitRaw(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10], parts[11], parts[12], parts[13], parts[14], parts[15], parts[16], parts[17], parts[18], parts[19], parts[20], parts[21], parts[22], parts[23], parts[24], parts[25], parts[26], parts[27], parts[28], parts[29]);
        return raw;
    }

    /**
     *
     */
    private void transformAndPersist() {
        // Récupère les marques déjà en base
        List<Marque> savedMarques = marqueRepository.findAll();
        Set<String> MarquesExistants = savedMarques.stream()
                .map(m -> m.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        // Marques à sauvegarder : uniquement les nouvelles
        List<Marque> marques = produitRaws.stream()
                .map(ProduitRaw::marque)
                .filter(Objects::nonNull)
                .flatMap(m -> Arrays.stream(m.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .distinct()
                .filter(nom -> !MarquesExistants.contains(nom))
                .map(nom -> new Marque(nom, nom))
                .toList();

        marqueRepository.saveAll(marques);


        //recup les existants en base
        List<Categorie> savedCategories = categorieRepository.findAll();
        Set<String> CategsExistants = savedCategories.stream()
                .map(c -> c.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        // Catégories à sauvegarder : uniquement les nouvelles
        List<Categorie> categories = produitRaws.stream()
                .map(ProduitRaw::categorie)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .distinct()
                .filter(nom -> !CategsExistants.contains(nom))
                .map(nom -> new Categorie(nom, nom))
                .toList();

        categorieRepository.saveAll(categories);


        //recup les existants en base
        List<Allergene> savedAllergenes = allergeneRepository.findAll();
        Set<String> AllergeneExistants = savedAllergenes.stream()
                .map(a -> a.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        // Allergenes à insérer : uniquement ceux qui n’existent pas déjà
        List<Allergene> allergenes = produitRaws.stream()
                .map(ProduitRaw::allergenes)
                .filter(Objects::nonNull)
                .flatMap(m -> Arrays.stream(m.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .distinct()
                .filter(nom -> !AllergeneExistants.contains(nom))
                .map(nom -> new Allergene(nom, nom))
                .toList();

        allergeneRepository.saveAll(allergenes);


        //recup les existants en base
        List<Additif> savedAdditifs = additifRepository.findAll();
        Set<String> AdditifsExistants = savedAdditifs.stream()
                .map(a -> a.getCode().toLowerCase().trim())
                .collect(Collectors.toSet());

        List<Additif> additifs = produitRaws.stream()
                .map(ProduitRaw::additifs)
                .filter(Objects::nonNull)
                .flatMap(a -> Arrays.stream(a.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .map(s -> {
                    String[] parts = s.split(" - ", 2);
                    String code = parts[0].trim().toLowerCase();
                    String nom = parts.length > 1 ? parts[1].trim() : "";
                    return new Additif(nom, code, nom);
                })
                .filter(a -> !AdditifsExistants.contains(a.getCode()))
                .toList();

        additifRepository.saveAll(additifs);


        //recup les existants en base
        //faire la diff basé sur le nom
        List<Ingredient> savedIngredients = ingredientRepository.findAll();
        Set<String> ingredientsExistants = savedIngredients.stream()
                .map(i -> i.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        List<Ingredient> ingredients = produitRaws.stream()
                .map(ProduitRaw::ingredients)
                .filter(Objects::nonNull)
                .flatMap(s -> Arrays.stream(s.split("[,]")))
                .map(String::trim)
                .filter(s -> s != null && !s.isEmpty())
                .map(String::toLowerCase)
                .distinct()
                .filter(nom -> !ingredientsExistants.contains(nom))
                .map(nom -> new Ingredient(nom, nom))
                .toList();

        ingredientRepository.saveAll(ingredients);

        List<Produit> produits = produitRaws.stream()
                .map(raw -> toProduit(raw, marques, categories, additifs, allergenes, ingredients))
                .filter(Objects::nonNull)
                .toList();

        produitRepository.saveAll(produits);

        List<Produit> savedPro = produitRepository.findAll();
        for (Produit p : savedPro) {
            log.error(p.toString());
        }

    }

    /**
     *
     * @param raw
     * @param allMarques
     * @param allCategories
     * @param allAdditifs
     * @param allAllergenes
     * @param allIngredients
     * @return
     */
    private Produit toProduit(ProduitRaw raw,
                             List<Marque> allMarques,
                             List<Categorie> allCategories,
                             List<Additif> allAdditifs,
                             List<Allergene> allAllergenes,
                             List<Ingredient> allIngredients) {


        Marque marque = allMarques.stream()
                .filter(m -> m.getId().equalsIgnoreCase(raw.marque()))
                .findFirst().orElse(null);

        Categorie categorie = allCategories.stream()
                .filter(c -> c.getId().equalsIgnoreCase(raw.categorie()))
                .findFirst().orElse(null);

        List<Additif> additifs = StringUtils.parseList(raw.additifs(), ",").stream()
                .map(code -> allAdditifs.stream()
                        .filter(a -> a.getCode().equalsIgnoreCase(code))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .toList();

        List<Allergene> allergenes = StringUtils.parseList(raw.allergenes(), ",").stream()
                .map(id -> allAllergenes.stream()
                        .filter(a -> a.getNom().equalsIgnoreCase(id))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .toList();

        List<Ingredient> ingredients = StringUtils.parseList(raw.ingredients(), ",").stream()
                .map(nom -> allIngredients.stream()
                        .filter(i -> i.getNom().equalsIgnoreCase(nom))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .toList();

        return new Produit(
                UUID.randomUUID().toString(),
                raw.nom(),
                raw.nutritionGradeFr(),

                StringUtils.parseFloat(raw.energie100g()),
                StringUtils.parseFloat(raw.graisse100g()),
                StringUtils.parseFloat(raw.sucres100g()),
                StringUtils.parseFloat(raw.fibres100g()),
                StringUtils.parseFloat(raw.proteines100g()),
                StringUtils.parseFloat(raw.sel100g()),

                StringUtils.parseFloat(raw.vitA100g()),
                StringUtils.parseFloat(raw.vitD100g()),
                StringUtils.parseFloat(raw.vitE100g()),
                StringUtils.parseFloat(raw.vitC100g()),
                StringUtils.parseFloat(raw.vitB1100g()),
                StringUtils.parseFloat(raw.vitB2100g()),
                StringUtils.parseFloat(raw.vitPP100g()),
                StringUtils.parseFloat(raw.vitB6100g()),
                StringUtils.parseFloat(raw.vitB9100g()),
                StringUtils.parseFloat(raw.vitB12100g()),

                StringUtils.parseFloat(raw.calcium100g()),
                StringUtils.parseFloat(raw.magnesium100g()),
                StringUtils.parseFloat(raw.iron100g()),
                StringUtils.parseFloat(raw.fer100g()),
                StringUtils.parseFloat(raw.betaCarotene100g()),

                StringUtils.parseBoolean(raw.presenceHuilePalme()),

                marque,
                categorie,
                ingredients,
                allergenes,
                additifs
        );
    }
}