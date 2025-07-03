package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdv.devduo.yukool.model.Additif;

import java.util.Optional;

@Repository
public interface AdditifRepository extends MongoRepository<Additif, String> {
    Optional<Additif> findByNom(String nom);
    Optional<Additif> findByCode(String code);
}
