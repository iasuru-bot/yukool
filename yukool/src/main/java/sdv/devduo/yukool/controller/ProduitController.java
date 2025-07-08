package sdv.devduo.yukool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdv.devduo.yukool.dto.ProduitDTO;
import sdv.devduo.yukool.model.Additif;
import sdv.devduo.yukool.service.ProduitService;
import sdv.devduo.yukool.model.Produit;
import java.util.List;
import java.util.Set;

/**
 *  Controller pour l'entité {@link Produit}
 */
@RestController
@RequestMapping("/products")
public class ProduitController {

    /** Instance de {@link ProduitService} */
    @Autowired
    private ProduitService produitService;

    // GET /products/top-by-brand?brand=X&limit=N
    /**
     * Endpoint pour recuperer les produits par marque
     * @param brand la marque
     * @param limit la limite
     * @return la liste des produits
     */
    @GetMapping("/top-by-brand")
    public ResponseEntity<Set<ProduitDTO>> getTopByBrand(@RequestParam String brand, @RequestParam int limit) {
        return ResponseEntity.ok(produitService.findTopByBrand(brand, limit));
    }

    // GET /products/top-by-category?category=X&limit=N
    /**
     *  Endpoint pour recupere les produits par categorie
     * @param category la category
     * @param limit la limite
     * @return la liste des produits
     */
    @GetMapping("/top-by-category")
    public ResponseEntity<Set<ProduitDTO>> getTopByCategory(@RequestParam String category, @RequestParam int limit) {
        return ResponseEntity.ok(produitService.findTopByCategory(category, limit));
    }

    // GET /products/top-by-brand-category?brand=X&category=Y&limit=N
    /**
     *  Endpoint pour recupere les produits par categorie et par marque
     * @param category la category
     * @param limit la limite
     * @param brand la marque
     * @return la liste des produits
     */
    @GetMapping("/top-by-brand-category")
    public ResponseEntity<Set<ProduitDTO>> getTopByBrandAndCategory(@RequestParam String brand, @RequestParam String category, @RequestParam int limit) {
        return ResponseEntity.ok(produitService.findTopByBrandAndCategory(brand, category, limit));
    }

} 