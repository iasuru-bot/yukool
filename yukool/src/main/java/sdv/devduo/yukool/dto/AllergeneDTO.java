package sdv.devduo.yukool.dto;

/**
 * DTO de {@link sdv.devduo.yukool.model.Allergene}
 * @param nom nom allergene
 * @param nbUtilisations nombre utilisations dans les produits
 */
public record AllergeneDTO(String nom, int nbUtilisations) {
}
