package sdv.devduo.yukool.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdv.devduo.yukool.model.Allergene;
import sdv.devduo.yukool.model.Produit;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité {@link Produit}
 */
@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {
    List<Produit> findByMarque_Id(String id, Pageable pageable);
    List<Produit> findByCategorie_Id(String id, Pageable pageable);
    List<Produit> findByMarque_IdAndCategorie_Id(String marqueId, String categorieId, Pageable pageable);
}
