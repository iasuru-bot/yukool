package sdv.devduo.yukool.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationSpELExpression;
import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.ProduitRaw;
import sdv.devduo.yukool.model.*;
import sdv.devduo.yukool.repository.*;
import sdv.devduo.yukool.utils.StringUtils;
import com.mongodb.DBRef;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@Slf4j
@Service
public class ProduitServiceImpl implements ProduitService{


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



    /**
     *
     */
    @Override
    public void saveAllProduit(List<ProduitRaw> produitRaws) {

        // Récupère les marques déjà en base
        List<Marque> savedMarques = marqueRepository.findAll();
        Set<String> MarquesExistants = savedMarques.stream()
                .map(m -> m.getNom().toLowerCase().trim())
                .collect(Collectors.toSet());

        // Marques à sauvegarder : uniquement les nouvelles
        List<Marque> marques = produitRaws.stream()
                .map(ProduitRaw::marque)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
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
                .filter(s -> !s.isEmpty())
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

        // Allergenes à insérer : uniquement ceux qui n'existent pas déjà
        List<Allergene> allergenes = produitRaws.stream()
                .map(ProduitRaw::allergenes)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(s -> !s.isEmpty())
                .distinct()
                .filter(nom -> !AllergeneExistants.contains(nom))
                .map(nom -> new Allergene(nom, nom))
                .toList();

        allergeneRepository.saveAll(allergenes);


        //recup les existants en base
        List<Additif> savedAdditifs = additifRepository.findAll();
        Set<String> additifsExistants = savedAdditifs.stream()
                .map(a -> a.getCode().toLowerCase().trim())
                .collect(Collectors.toSet());

        List<Additif> additifs = produitRaws.stream()
                .flatMap(produitRaw -> produitRaw.additifs().entrySet().stream())
                .map(entry -> new Additif(entry.getKey(), entry.getValue(),entry.getKey()))
                .filter(additif -> !additifsExistants.contains(additif.getCode().toLowerCase().trim()))
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
                .flatMap(List::stream)
                .filter(s -> !s.isEmpty())
                .distinct()
                .filter(nom -> !ingredientsExistants.contains(nom))
                .map(nom -> new Ingredient(nom, nom))
                .toList();

        ingredientRepository.saveAll(ingredients);

        List<Produit> produits = produitRaws.stream()
                .map(raw -> toProduit(raw, marques, categories, additifs, allergenes, ingredients))
                .toList();

        produitRepository.saveAll(produits);

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

        List<Additif> additifs = new ArrayList<>();
        for (Map.Entry<String, String> entry : raw.additifs().entrySet()) {
            additifs.add(allAdditifs.stream()
                    .filter(a -> a.getCode().equalsIgnoreCase(entry.getKey()))
                    .findFirst().orElse(null));
        }
        List<Allergene> allergenes = raw.allergenes().stream()
                .map(id -> allAllergenes.stream()
                        .filter(a -> a.getNom().equalsIgnoreCase(id))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .toList();

        List<Ingredient> ingredients = raw.ingredients().stream()
                .map(nom -> allIngredients.stream()
                        .filter(i -> i.getNom().equalsIgnoreCase(nom))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
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

    @Override
    public List<Produit> findTopByBrand(String brand, int limit) {
        Optional<Marque> marque = marqueRepository.findByNomIgnoreCase(brand);
        if (marque.isEmpty()) return List.of();
        return produitRepository.findByMarque_Id(marque.get().getId(), PageRequest.of(0, limit));
    }

    @Override
    public List<Produit> findTopByCategory(String category, int limit) {
        Optional<Categorie> categorie = categorieRepository.findByNomIgnoreCase(category);
        if (categorie.isEmpty()) return List.of();
        return produitRepository.findByCategorie_Id(categorie.get().getId(), PageRequest.of(0, limit));
    }

    @Override
    public List<Produit> findTopByBrandAndCategory(String brand, String category, int limit) {
        Optional<Marque> marque = marqueRepository.findByNomIgnoreCase(brand);
        Optional<Categorie> categorie = categorieRepository.findByNomIgnoreCase(category);
        if (marque.isEmpty() || categorie.isEmpty()) return List.of();
        return produitRepository.findByMarque_IdAndCategorie_Id(marque.get().getId(), categorie.get().getId(), PageRequest.of(0, limit));
    }

    @Override
    public List<Ingredient> findTopIngredients(int limit) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.unwind("ingredients"),
                Aggregation.group("ingredients").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(limit),
                Aggregation.project("_id")
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "produits", Document.class);
        List<String> ingredientIds = results.getMappedResults().stream()
            .map(doc -> {
                Object ref = doc.get("_id");
                if (ref instanceof DBRef) {
                    return ((DBRef) ref).getId().toString();
                } else if (ref instanceof String) {
                    return (String) ref;
                } else {
                    return ref != null ? ref.toString() : null;
                }
            })
            .filter(Objects::nonNull)
            .toList();
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        Map<String, Ingredient> ingredientMap = ingredients.stream().collect(Collectors.toMap(Ingredient::getId, i -> i));
        return ingredientIds.stream().map(ingredientMap::get).filter(Objects::nonNull).toList();
    }

    @Override
    public List<Allergene> findTopAllergenes(int limit) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.unwind("allergenes"),
                Aggregation.group("allergenes").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(limit),
                Aggregation.project("_id")
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(agg, "produits", Document.class);
        List<String> allergeneIds = results.getMappedResults().stream()
            .map(doc -> {
                Object ref = doc.get("_id");
                if (ref instanceof DBRef) {
                    return ((DBRef) ref).getId().toString();
                } else if (ref instanceof String) {
                    return (String) ref;
                } else {
                    return ref != null ? ref.toString() : null;
                }
            })
            .filter(Objects::nonNull)
            .toList();
        List<Allergene> allergenes = allergeneRepository.findAllById(allergeneIds);
        Map<String, Allergene> allergeneMap = allergenes.stream().collect(Collectors.toMap(Allergene::getId, a -> a));
        return allergeneIds.stream().map(allergeneMap::get).filter(Objects::nonNull).toList();
    }

    @Override
    public List<Additif> findTopAdditifs(int limit) {
       
    }
}