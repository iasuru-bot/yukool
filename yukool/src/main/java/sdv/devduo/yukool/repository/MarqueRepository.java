package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sdv.devduo.yukool.model.Marque;

import java.util.Optional;

public interface MarqueRepository extends MongoRepository<Marque, String> {
    Optional<Marque> findByNom(String nom);
}
