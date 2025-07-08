package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdv.devduo.yukool.model.Additif;
import sdv.devduo.yukool.model.Allergene;

import java.util.Optional;

/**
 * Repository pour l'entité {@link Additif}
 */
@Repository
public interface AdditifRepository extends MongoRepository<Additif, String> {
}
