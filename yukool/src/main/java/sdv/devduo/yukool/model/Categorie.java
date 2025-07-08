package sdv.devduo.yukool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Categorie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categories")
public class Categorie {

    @Id
    private String id;

    /** nom d'une categorie*/
    private String nom;
}
