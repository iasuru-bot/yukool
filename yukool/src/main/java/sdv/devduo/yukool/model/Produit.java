package sdv.devduo.yukool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

/** Le produit */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "produits")
public class Produit {
    @Id
    private String id;

    /** nom du produit*/
    private String nom;

    /** infos sur le produit*/
    private NutritionGradeFr nutriScore;

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
    private Float vitK100g;
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

    /** la marque du produit*/
    @DBRef
    private Marque marque;

    /** la categorie du produit*/
    @DBRef
    private Categorie categorie;

    /** la liste des ingredients du produit*/
    private List<String> ingredients;

    /** la liste des allergenes du produit*/
    private List<String> allergenes;

    /** la liste des additifs du produit*/
    private List<String> additifs;
}
