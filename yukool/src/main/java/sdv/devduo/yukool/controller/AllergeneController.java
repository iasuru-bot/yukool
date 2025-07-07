package sdv.devduo.yukool.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.model.Ingredient;
import sdv.devduo.yukool.service.AllergeneService;
import sdv.devduo.yukool.service.ProduitService;

import java.util.List;

@RestController
@RequestMapping("/allergens")
public class AllergeneController {

    @Autowired
    private ProduitService produitService;

    // GET /allergens/top?limit=N
    @GetMapping("/top")
    public List<Allergene> getTopAllergene(@RequestParam int limit) {
        return produitService.findTopAllergenes(limit);
    }
}
