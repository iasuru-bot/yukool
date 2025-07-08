package sdv.devduo.yukool.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sdv.devduo.yukool.dto.AdditifDTO;
import sdv.devduo.yukool.model.Additif;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.service.ProduitService;

import java.util.List;
import java.util.Set;


/**
 *  Controller pour l'entité {@link Additif}
 */
@RestController
@RequestMapping("/additives")
public class AdditifController {

    /** Instance de {@link ProduitService} */
    @Autowired
    private ProduitService produitService;

    // GET /additives/top?limit=N
    /**
     * Endpoint pour recupérer la liste des additives les plus présents
     * @param limit la limite
     * @return la liste des additives
     */
    @GetMapping("/top")
    public ResponseEntity<List<AdditifDTO>> getTopAdditifs(@RequestParam int limit) {
        return ResponseEntity.ok(produitService.findTopAdditifs(limit));
    }
}
