package sdv.devduo.yukool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sdv.devduo.yukool.service.ProduitService;
import sdv.devduo.yukool.model.Produit;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    // GET /products/top-by-brand?brand=X&limit=N
    @GetMapping("/top-by-brand")
    public List<Produit> getTopByBrand(@RequestParam String brand, @RequestParam int limit) {
        return produitService.findTopByBrand(brand, limit);
    }

    // GET /products/top-by-category?category=X&limit=N
    @GetMapping("/top-by-category")
    public List<Produit> getTopByCategory(@RequestParam String category, @RequestParam int limit) {
        return produitService.findTopByCategory(category, limit);
    }

    // GET /products/top-by-brand-category?brand=X&category=Y&limit=N
    @GetMapping("/top-by-brand-category")
    public List<Produit> getTopByBrandAndCategory(@RequestParam String brand, @RequestParam String category, @RequestParam int limit) {
        return produitService.findTopByBrandAndCategory(brand, category, limit);
    }

} 