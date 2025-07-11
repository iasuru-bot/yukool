package sdv.devduo.yukool.dto;


import java.util.HashMap;
import java.util.List;

/**
 *  DTO représentant une ligne du CSV d'import
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
public record ProduitRaw(String categorie, String marque, String nom, String nutritionGradeFr, List<String> ingredients, String energie100g
        , String graisse100g, String sucres100g, String fibres100g, String proteines100g, String sel100g, String vitA100g
        , String vitD100g, String vitE100g, String vitK100g, String vitC100g, String vitB1100g, String vitB2100g, String vitPP100g
        , String vitB6100g, String vitB9100g, String vitB12100g, String calcium100g, String magnesium100g, String iron100g
        , String fer100g, String betaCarotene100g, String presenceHuilePalme, List<String> allergenes, HashMap<String,String> additifs) {
}