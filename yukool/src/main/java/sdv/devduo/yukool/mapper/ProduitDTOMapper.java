package sdv.devduo.yukool.mapper;


import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.ProduitDTO;
import sdv.devduo.yukool.model.Additif;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.model.Ingredient;
import sdv.devduo.yukool.model.Produit;

import java.util.Map;
import java.util.Objects;


public final class ProduitDTOMapper {

    public static ProduitDTO toDto(Produit produit,
                            Map<String, Ingredient> ingredientMap,
                            Map<String, Additif> additifMap,
                            Map<String, Allergene> allergeneMap) {
        return new ProduitDTO(produit.getCategorie() != null ? produit.getCategorie().getNom() : null,
        produit.getMarque() != null ? produit.getMarque().getNom() : null,
                produit.getNom(),
                produit.getNutriScore(),

                produit.getIngredients().stream()
                        .map(ingredientMap::get)
                        .filter(Objects::nonNull)
                        .map(Ingredient::getNom)
                        .toList(),

                produit.getEnergie100g(),
                produit.getGraisse100g(),
                produit.getSucres100g(),
                produit.getFibres100g(),
                produit.getProteines100g(),
                produit.getSel100g(),

                produit.getVitA100g(),
                produit.getVitD100g(),
                produit.getVitE100g(),
                produit.getVitK100g(),
                produit.getVitC100g(),
                produit.getVitB1100g(),
                produit.getVitB2100g(),
                produit.getVitPP100g(),
                produit.getVitB6100g(),
                produit.getVitB9100g(),
                produit.getVitB12100g(),

                produit.getCalcium100g(),
                produit.getMagnesium100g(),
                produit.getIron100g(),
                produit.getFer100g(),
                produit.getBetaCarotene100g(),
                produit.getPresenceHuilePalme(),

                produit.getAllergenes().stream()
                        .map(allergeneMap::get)
                        .filter(Objects::nonNull)
                        .map(Allergene::getNom)
                        .toList(),

                produit.getAdditifs().stream()
                        .map(additifMap::get)
                        .filter(Objects::nonNull)
                        .map(Additif::getNom)
                        .toList()
    );

    }
}
