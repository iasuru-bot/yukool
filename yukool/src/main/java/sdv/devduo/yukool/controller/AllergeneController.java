package sdv.devduo.yukool.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sdv.devduo.yukool.dto.AllergeneDTO;
import sdv.devduo.yukool.model.Additif;
import sdv.devduo.yukool.service.ProduitService;

import java.util.List;

/**
 *  Controller pour l'entité {@link sdv.devduo.yukool.model.Allergene}
 */
@RestController
@RequestMapping("/allergens")
public class AllergeneController {

    /** Instance de {@link ProduitService} */
    @Autowired
    private ProduitService produitService;

    // GET /allergens/top?limit=N
    /**
     * Endpoint pour recupérer la liste des allergenes les plus présents
     * @param limit la limite
     * @return la liste des allergenes
     */
    @GetMapping("/top")
    public ResponseEntity<List<AllergeneDTO>> getTopAllergene(@RequestParam int limit) {
        return ResponseEntity.ok(produitService.findTopAllergenes(limit));
    }
}
