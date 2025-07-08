package sdv.devduo.yukool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Un additif
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "additifs")
public class Additif {

    @Id
    private String id;

    /** nom de l'additif */
    private String nom;

    /** code de l'additif */
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Additif additif)) return false;
        return code != null && code.equalsIgnoreCase(additif.code);
    }

    @Override
    public int hashCode() {
        return code == null ? 0 : code.toLowerCase().hashCode();
    }
}