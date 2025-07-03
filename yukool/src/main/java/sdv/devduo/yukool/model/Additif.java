package sdv.devduo.yukool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "additifs")
public class Additif {

    @Id
    private String id;

    private String nom;

    private String code;
}
