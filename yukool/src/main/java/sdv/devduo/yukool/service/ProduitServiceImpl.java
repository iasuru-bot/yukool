package sdv.devduo.yukool.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.*;
import sdv.devduo.yukool.mapper.ProduitDTOMapper;
import sdv.devduo.yukool.model.*;
import sdv.devduo.yukool.repository.*;
import sdv.devduo.yukool.utils.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implémentation du service {@link ProduitService}
 */
@Slf4j
@Service
public class ProduitServiceImpl implements ProduitService {


    private final MarqueRepository marqueRepository;

    private final CategorieRepository categorieRepository;

    private final IngredientRepository ingredientRepository;

    private final AdditifRepository additifRepository;

    private final AllergeneRepository allergeneRepository;

    private final ProduitRepository produitRepository;
    private final MongoTemplate mongoTemplate;


    @Autowired
    public ProduitServiceImpl(MarqueRepository marqueRepository, CategorieRepository categorieRepository, IngredientRepository ingredientRepository, AdditifRepository additifRepository, AllergeneRepository allergeneRepository, ProduitRepository produitRepository, MongoTemplate mongoTemplate) {
        this.marqueRepository = marqueRepository;
        this.categorieRepository = categorieRepository;
        this.ingredientRepository = ingredientRepository;
        this.additifRepository = additifRepository;
        this.allergeneRepository = allergeneRepository;
        this.produitRepository = produitRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveAllProduit(List<ProduitRaw> produitRaws) {

        // Récupère les marques déjà en base
        List<Marque> savedMarques = marqueRepository.findAll();
        Set<String> MarquesExistants = savedMarques.stream()
                .map(m -> m.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        // Marques à sauvegarder : uniquement les nouvelles
        Set<Marque> marques = produitRaws.stream()
                .map(ProduitRaw::marque)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .distinct()
                .filter(nom -> !MarquesExistants.contains(nom))
                .map(nom -> new Marque(UUID.randomUUID().toString(), nom))
                .collect(Collectors.toSet());

        marqueRepository.saveAll(marques);


        //recup les existants en base
        List<Categorie> savedCategories = categorieRepository.findAll();
        Set<String> CategsExistants = savedCategories.stream()
                .map(c -> c.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        // Catégories à sauvegarder : uniquement les nouvelles
        Set<Categorie> categories = produitRaws.stream()
                .map(ProduitRaw::categorie)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .distinct()
                .filter(nom -> !CategsExistants.contains(nom))
                .map(nom -> new Categorie(UUID.randomUUID().toString(), nom))
                .collect(Collectors.toSet());

        categorieRepository.saveAll(categories);


        //recup les existants en base
        List<Allergene> savedAllergenes = allergeneRepository.findAll();
        Set<String> AllergeneExistants = savedAllergenes.stream()
                .map(a -> a.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        // Allergenes à insérer : uniquement ceux qui n'existent pas déjà
        List<Allergene> allergenes = produitRaws.stream()
                .map(ProduitRaw::allergenes)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(s -> !s.isEmpty())
                .distinct()
                .filter(nom -> !AllergeneExistants.contains(nom))
                .map(nom -> new Allergene(UUID.randomUUID().toString(), nom.trim()))
                .toList();

        allergeneRepository.saveAll(allergenes);



        //recup les existants en base
        List<Additif> savedAdditifs = additifRepository.findAll();
        Set<String> additifsExistants = savedAdditifs.stream()
                .map(a -> a.getCode().toLowerCase().trim())
                .collect(Collectors.toSet());

        Set<Additif> additifs = produitRaws.stream()
                .flatMap(produitRaw -> produitRaw.additifs().entrySet().stream())
                .map(entry -> new Additif(UUID.randomUUID().toString(), entry.getValue(), entry.getKey()))
                .filter(additif -> !additifsExistants.contains(additif.getCode().toLowerCase().trim()))
                .collect(Collectors.toSet());
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
                .flatMap(List::stream)
                .filter(s -> !s.isEmpty())
                .distinct()
                .filter(nom -> !ingredientsExistants.contains(nom))
                .map(nom -> new Ingredient(
                        UUID.randomUUID().toString(), nom.trim()))
                .toList();

        ingredientRepository.saveAll(ingredients);


        // get all marques , categories ,additifs et allergenes ,et ingredients
        List<Marque> allMarques = marqueRepository.findAll();
        List<Categorie> allCategorie = categorieRepository.findAll();
        List<Additif> allAdditif = additifRepository.findAll();
        List<Allergene> allAllergene = allergeneRepository.findAll();
        List<Ingredient> allIngredient = ingredientRepository.findAll();

        List<Produit> produits = produitRaws.stream()
                .map(raw -> toProduit(raw, allMarques, allCategorie, allAdditif, allAllergene, allIngredient))
                .toList();

        produitRepository.saveAll(produits);

    }

    /**
     * Fonction pour passer d'un {@link ProduitRaw} à un {@link  Produit}
     * @param raw le produitRaw
     * @param allMarques toutes les marques
     * @param allCategories toutes les categories
     * @param allAdditifs tous les additifs
     * @param allAllergenes tous les allergenes
     * @param allIngredients tous les ingrédients
     * @return le produit
     */
    private Produit toProduit(ProduitRaw raw,
                              List<Marque> allMarques,
                              List<Categorie> allCategories,
                              List<Additif> allAdditifs,
                              List<Allergene> allAllergenes,
                              List<Ingredient> allIngredients) {

        // Marque
        Marque marque = allMarques.stream()
                .filter(m -> m.getNom().equalsIgnoreCase(raw.marque()))
                .findFirst().orElse(null);

        // Categorie
        Categorie categorie = allCategories.stream()
                .filter(c -> c.getNom().equalsIgnoreCase(raw.categorie()))
                .findFirst().orElse(null);

        // Additifs
        List<String> additifsIds = raw.additifs().keySet().stream()
                .map(s -> allAdditifs.stream()
                        .filter(a -> a.getCode().equalsIgnoreCase(s))
                        .findFirst()
                        .map(Additif::getId)
                        .orElse(null))
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        // Allergènes
        List<String> allergenesIds = raw.allergenes().stream()
                .map(nom -> allAllergenes.stream()
                        .filter(a -> a.getNom().equalsIgnoreCase(nom.trim().toLowerCase()))
                        .findFirst()
                        .map(Allergene::getId)
                        .orElse(null))
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // Ingredients
        List<String> ingredientsIds = raw.ingredients().stream()
                .map(nom -> allIngredients.stream()
                        .filter(i -> i.getNom().equalsIgnoreCase(nom.trim().toLowerCase()))
                        .findFirst()
                        .map(Ingredient::getId)
                        .orElse(null))
                .filter(Objects::nonNull)
                .distinct()
                .toList();


        return new Produit(
                UUID.randomUUID().toString(),
                raw.nom(),
                NutritionGradeFr.fromString(raw.nutritionGradeFr()),

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
                StringUtils.parseFloat(raw.vitK100g()),
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
                ingredientsIds,
                allergenesIds,
                additifsIds
        );
    }

    @Override
    public Set<ProduitDTO> findTopByBrand(String brand, int limit) {
        Optional<Marque> marque = marqueRepository.findByNomIgnoreCase(brand);
        if (marque.isEmpty()) return Set.of();
        List<Produit> produits = produitRepository.findByMarque_Id(
                marque.get().getId(), PageRequest.of(0, limit));

        // Préparer les maps pour lookup rapide par ID
        Map<String, Ingredient> ingredientMap = ingredientRepository.findAll().stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
        Map<String, Additif> additifMap = additifRepository.findAll().stream()
                .collect(Collectors.toMap(Additif::getId, Function.identity()));
        Map<String, Allergene> allergeneMap = allergeneRepository.findAll().stream()
                .collect(Collectors.toMap(Allergene::getId, Function.identity()));

        return produits.stream()
                .map(p -> ProduitDTOMapper.toDto(p, ingredientMap, additifMap, allergeneMap))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ProduitDTO> findTopByCategory(String category, int limit) {
        String cleanCategory = category == null ? "" : category.trim();


        Optional<Categorie> categorie = categorieRepository.findByNomIgnoreCase(cleanCategory);
        if (categorie.isEmpty()) {
            log.error("Catégorie non trouvée pour '{}'", cleanCategory);
            return Set.of();
        }

        List<Produit> produits = produitRepository.findByCategorie_Id(
                categorie.get().getId(), PageRequest.of(0, limit));

        // Préparer les maps pour lookup rapide par ID
        Map<String, Ingredient> ingredientMap = ingredientRepository.findAll().stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
        Map<String, Additif> additifMap = additifRepository.findAll().stream()
                .collect(Collectors.toMap(Additif::getId, Function.identity()));
        Map<String, Allergene> allergeneMap = allergeneRepository.findAll().stream()
                .collect(Collectors.toMap(Allergene::getId, Function.identity()));

        return  produits.stream()
                .map(p -> ProduitDTOMapper.toDto(p, ingredientMap, additifMap, allergeneMap))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ProduitDTO> findTopByBrandAndCategory(String brand, String category, int limit) {
        Optional<Marque> marque = marqueRepository.findByNomIgnoreCase(brand);
        Optional<Categorie> categorie = categorieRepository.findByNomIgnoreCase(category);
        if (marque.isEmpty() || categorie.isEmpty()) return Set.of();

        List<Produit> produits = produitRepository.findByMarque_IdAndCategorie_Id(marque.get().getId(), categorie.get().getId(), PageRequest.of(0, limit));


        // Préparer les maps pour lookup rapide par ID
        Map<String, Ingredient> ingredientMap = ingredientRepository.findAll().stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
        Map<String, Additif> additifMap = additifRepository.findAll().stream()
                .collect(Collectors.toMap(Additif::getId, Function.identity()));
        Map<String, Allergene> allergeneMap = allergeneRepository.findAll().stream()
                .collect(Collectors.toMap(Allergene::getId, Function.identity()));

        return  produits.stream()
                .map(p -> ProduitDTOMapper.toDto(p, ingredientMap, additifMap, allergeneMap))
                .collect(Collectors.toSet());
    }

    @Override
    public List<IngredientDTO> findTopIngredients(int limit) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.unwind("ingredients"),
                Aggregation.group("ingredients").count().as("nbUtilisations"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "nbUtilisations")),
                Aggregation.limit(limit),
                Aggregation.lookup("ingredients", "_id", "_id", "ingredient"),
                Aggregation.unwind("ingredient"),
                Aggregation.project()
                        .and("ingredient.nom").as("nom")
                        .and("nbUtilisations").as("nbUtilisations")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "produits", Document.class);

        return results.getMappedResults().stream()
                .map(doc -> new IngredientDTO(
                        doc.getString("nom"),
                        doc.getInteger("nbUtilisations")
                ))
                .toList();
    }

    @Override
    public List<AllergeneDTO> findTopAllergenes(int limit) {

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.unwind("allergenes"),
                Aggregation.group("allergenes").count().as("nbUtilisations"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "nbUtilisations")),
                Aggregation.limit(limit),
                Aggregation.lookup("allergenes", "_id", "_id", "allergene"),
                Aggregation.unwind("allergene"),
                Aggregation.project()
                        .and("allergene.nom").as("nom")
                        .and("nbUtilisations").as("nbUtilisations")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "produits", Document.class);

        return results.getMappedResults().stream()
                .map(doc -> new AllergeneDTO(
                        doc.getString("nom"),
                        doc.getInteger("nbUtilisations")
                ))
                .toList();
    }


    @Override
    public List<AdditifDTO> findTopAdditifs(int limit) {

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.unwind("additifs"),
                Aggregation.group("additifs").count().as("nbUtilisations"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "nbUtilisations")),
                Aggregation.limit(limit),
                Aggregation.lookup("additifs", "_id", "_id", "additif"),
                Aggregation.unwind("additif"),
                Aggregation.project()
                        .and("additif.nom").as("nom")
                        .and("additif.code").as("code")
                        .and("nbUtilisations").as("nbUtilisations")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "produits", Document.class);

        return results.getMappedResults().stream()
                .map(doc -> new AdditifDTO(
                        doc.getString("nom"),
                        doc.getString("code"),
                        doc.getInteger("nbUtilisations")
                ))
                .toList();
    }
}