package sdv.devduo.yukool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sdv.devduo.yukool.model.Ingredient;
import sdv.devduo.yukool.service.ProduitService;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private ProduitService produitService;

    // GET /ingredients/top?limit=N
    @GetMapping("/top")
    public List<Ingredient> getTopIngredients(@RequestParam int limit) {
        return produitService.findTopIngredients(limit);
    }
}
