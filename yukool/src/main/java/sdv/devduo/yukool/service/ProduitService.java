package sdv.devduo.yukool.service;

import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.ProduitRaw;
import sdv.devduo.yukool.model.Produit;
import sdv.devduo.yukool.model.Ingredient;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.model.Additif;

import java.util.List;

@Service
public interface ProduitService {

    void saveAllProduit(List<ProduitRaw> produitRaws);

    List<Produit> findTopByBrand(String brand, int limit);
    List<Produit> findTopByCategory(String category, int limit);
    List<Produit> findTopByBrandAndCategory(String brand, String category, int limit);
    List<Ingredient> findTopIngredients(int limit);
    List<Allergene> findTopAllergenes(int limit);
    List<Additif> findTopAdditifs(int limit);
}
