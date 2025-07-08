package sdv.devduo.yukool.dto;

/**
 * DTO de {@link sdv.devduo.yukool.model.Additif}
 * @param nom nom additif
 * @param code code additif
 * @param nbUtilisations nombre utilisations dans les produits
 */
public record AdditifDTO(String nom,String code, int nbUtilisations) {
}
