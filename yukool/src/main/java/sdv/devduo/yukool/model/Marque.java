package sdv.devduo.yukool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Marque
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "marques")
public class Marque {

    @Id
    private String id;

    /** nom d'une marque*/
    private String nom;
}
