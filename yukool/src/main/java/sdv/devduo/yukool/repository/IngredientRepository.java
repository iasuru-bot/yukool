package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdv.devduo.yukool.model.Ingredient;

import java.util.Optional;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    Optional<Ingredient> findByNom(String nom);
}
