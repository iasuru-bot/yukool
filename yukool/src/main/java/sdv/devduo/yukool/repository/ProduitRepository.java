package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sdv.devduo.yukool.model.Produit;

import java.util.Optional;

public interface ProduitRepository extends MongoRepository<Produit, String> {
    Optional<Produit> findByNom(String nom);
}
