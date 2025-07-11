package sdv.devduo.yukool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Allergene
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "allergenes")
public class Allergene {
    @Id
    private String id;

    /** nom allergene */
    private String nom;

}
