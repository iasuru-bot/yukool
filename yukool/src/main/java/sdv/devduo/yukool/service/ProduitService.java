package sdv.devduo.yukool.service;

import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.*;
import sdv.devduo.yukool.model.Produit;
import sdv.devduo.yukool.model.Ingredient;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.model.Additif;

import java.util.List;
import java.util.Set;

/**
 * Service pour les produits
 */
@Service
public interface ProduitService {

    /**
     * Fonction pour sauvegarder une liste de produits
     * @param produitRaws la liste des produits
     */
    void saveAllProduit(List<ProduitRaw> produitRaws);

    /**
     * Recuperer les produits par marque
     * @param brand la marque
     * @param limit la limite
     * @return une liste de produit
     */
    Set<ProduitDTO> findTopByBrand(String brand, int limit);

    /**
     * Recuperer les produits par categorie
     * @param category la catégorie
     * @param limit la limite
     * @return une liste de produit
     */
    Set<ProduitDTO> findTopByCategory(String category, int limit);

    /**
     * Recuperer les produits par categorie et par marque
     * @param brand la marque
     * @param category la catégorie
     * @param limit la limite
     * @return une liste de produit
     */
    Set<ProduitDTO> findTopByBrandAndCategory(String brand, String category, int limit);

    /**
     * Recupere les ingredients les plus utilisés dans les produits
     * @param limit la limite
     * @return une liste d'ingredients
     */
    List<IngredientDTO> findTopIngredients(int limit);

    /**
     * Recupere les allergenes les plus utilisés dans les produits
     * @param limit la limite
     * @return une liste d'allergenes
     */
    List<AllergeneDTO> findTopAllergenes(int limit);

    /**
     * Recupere les additifs les plus utilisés dans les produits
     * @param limit la limite
     * @return une liste d'additifs
     */
    List<AdditifDTO> findTopAdditifs(int limit);
}
