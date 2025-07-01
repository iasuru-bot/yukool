package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sdv.devduo.yukool.model.Categorie;

import java.util.Optional;

public interface CategorieRepository extends MongoRepository<Categorie, String> {
    Optional<Categorie> findByNom(String nom);
}
