package sdv.devduo.yukool.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdv.devduo.yukool.model.Produit;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {
    Optional<Produit> findByNom(String nom);
    List<Produit> findByMarque_NomIgnoreCase(String nom, Pageable pageable);
    List<Produit> findByCategorie_NomIgnoreCase(String nom, Pageable pageable);
    List<Produit> findByMarque_NomIgnoreCaseAndCategorie_NomIgnoreCase(String marque, String categorie, Pageable pageable);
    List<Produit> findByMarque_Id(String id, Pageable pageable);
    List<Produit> findByCategorie_Id(String id, Pageable pageable);
    List<Produit> findByMarque_IdAndCategorie_Id(String marqueId, String categorieId, Pageable pageable);
}
