package sdv.devduo.yukool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "produits")
public class Produit {
    @Id
    private UUID id;

    private String nom;
    private String nutriScore;

    private Float energie100g;
    private Float graisse100g;
    private Float sucres100g;
    private Float fibres100g;
    private Float proteines100g;
    private Float sel100g;

    private Float vitA100g;
    private Float vitD100g;
    private Float vitE100g;
    private Float vitC100g;
    private Float vitB1100g;
    private Float vitB2100g;
    private Float vitPP100g;
    private Float vitB6100g;
    private Float vitB9100g;
    private Float vitB12100g;

    private Float calcium100g;
    private Float magnesium100g;
    private Float iron100g;
    private Float fer100g;
    private Float betaCarotene100g;

    private Boolean presenceHuilePalme;

    @DBRef
    private Marque marque;

    @DBRef
    private Categorie categorie;

    @DBRef
    private List<Ingredient> ingredients;

    @DBRef
    private List<Allergene> allergenes;

    @DBRef
    private List<Additif> additifs;
}
