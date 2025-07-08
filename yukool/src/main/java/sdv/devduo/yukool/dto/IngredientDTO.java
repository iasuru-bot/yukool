package sdv.devduo.yukool.dto;

/**
 * DTO de {@link sdv.devduo.yukool.model.Ingredient}
 * @param nom nom ingredients
 * @param nbUtilisations nombre utilisations dans les produits
 */
public record IngredientDTO (String nom, int nbUtilisations){
}
