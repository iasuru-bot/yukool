package sdv.devduo.yukool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sdv.devduo.yukool.dto.IngredientDTO;
import sdv.devduo.yukool.model.Additif;
import sdv.devduo.yukool.model.Ingredient;
import sdv.devduo.yukool.service.ProduitService;

import java.util.List;
import java.util.Set;

/**
 *  Controller pour l'entité {@link Ingredient}
 */
@RestController
@RequestMapping("/ingredients")
public class IngredientController {


    /** Instance de {@link ProduitService} */
    @Autowired
    private ProduitService produitService;

    // GET /ingredients/top?limit=N
    /**
     * Endpoint pour recupérer la liste des ingredients les plus présents
     * @param limit la limite
     * @return la liste des ingredients
     */
    @GetMapping("/top")
    public ResponseEntity<List<IngredientDTO>> getTopIngredients(@RequestParam int limit) {
        return ResponseEntity.ok(produitService.findTopIngredients(limit));
    }
}
