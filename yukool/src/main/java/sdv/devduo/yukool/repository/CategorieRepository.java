package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.model.Categorie;

import java.util.Optional;

/**
 * Repository pour l'entité {@link Categorie}
 */
@Repository
public interface CategorieRepository extends MongoRepository<Categorie, String> {
    Optional<Categorie> findByNomIgnoreCase(String nom);
}
