package sdv.devduo.yukool.dto;

import sdv.devduo.yukool.model.NutritionGradeFr;

import java.util.HashMap;
import java.util.List;

/**
 *  DTO représentant {@link sdv.devduo.yukool.model.Produit}
 * @param categorie categorie du produit
 * @param marque marque du produit
 * @param nom nom du produit
 * @param nutritionGradeFr nutritionGradeFr du produit
 * @param ingredients ingredients du produit
 * @param energie100g energie100g du produit
 * @param graisse100g graisse100g du produit
 * @param sucres100g sucres100g du produit
 * @param fibres100g fibres100g du produit
 * @param proteines100g proteines100g du produit
 * @param sel100g sel100g du produit
 * @param vitA100g vitA100g du produit
 * @param vitD100g vitD100g du produit
 * @param vitE100g vitE100g du produit
 * @param vitK100g vitK100g du produit
 * @param vitC100g vitC100g du produit
 * @param vitB1100g vitB1100g du produit
 * @param vitB2100g vitB2100g du produit
 * @param vitPP100g vitPP100g du produit
 * @param vitB6100g vitB6100g du produit
 * @param vitB9100g vitB9100g du produit
 * @param vitB12100g vitB12100g du produit
 * @param calcium100g calcium100g du produit
 * @param magnesium100g magnesium100g du produit
 * @param iron100g iron100g du produit
 * @param fer100g fer100g du produit
 * @param betaCarotene100g betaCarotene100g du produit
 * @param presenceHuilePalme presenceHuilePalme du produit
 * @param allergenes allergenes du produit
 * @param additifs additifs du produit
 */
public record ProduitDTO(String categorie, String marque, String nom, NutritionGradeFr nutritionGradeFr, List<String> ingredients, Float energie100g
        , Float graisse100g, Float sucres100g, Float fibres100g, Float proteines100g, Float sel100g, Float vitA100g
        , Float vitD100g, Float vitE100g,  Float vitC100g,Float vitK100g, Float vitB1100g, Float vitB2100g, Float vitPP100g
        , Float vitB6100g, Float vitB9100g, Float vitB12100g, Float calcium100g, Float magnesium100g, Float iron100g
        , Float fer100g, Float betaCarotene100g, boolean presenceHuilePalme, List<String> allergenes, List<String> additifs) {
}