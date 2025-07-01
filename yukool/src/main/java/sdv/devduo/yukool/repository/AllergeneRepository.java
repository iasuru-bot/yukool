package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sdv.devduo.yukool.model.Allergene;

import java.util.Optional;

public interface AllergeneRepository extends MongoRepository<Allergene, String> {
    Optional<Allergene> findByNom(String nom);
}
