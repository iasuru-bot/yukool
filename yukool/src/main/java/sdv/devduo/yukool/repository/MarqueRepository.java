package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.model.Marque;

import java.util.Optional;


/**
 * Repository pour l'entité {@link Marque}
 */
@Repository
public interface MarqueRepository extends MongoRepository<Marque, String> {
    Optional<Marque> findByNomIgnoreCase(String nom);
}
