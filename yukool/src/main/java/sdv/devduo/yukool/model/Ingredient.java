package sdv.devduo.yukool.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;


/**
 * Ingredient
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ingredients")
public class Ingredient {

    @Id
    private String id;

    /** Nom d'un ingredient*/
    private String nom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient that)) return false;
        return nom != null && nom.equalsIgnoreCase(that.nom);
    }

    @Override
    public int hashCode() {
        return nom == null ? 0 : nom.toLowerCase().hashCode();
    }
}
