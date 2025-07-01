package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sdv.devduo.yukool.model.Additif;

import java.util.Optional;

public interface AdditifRepository extends MongoRepository<Additif, String> {
    Optional<Additif> findByNom(String nom);
    Optional<Additif> findByCode(String code);
}
