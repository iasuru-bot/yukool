package sdv.devduo.yukool.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sdv.devduo.yukool.model.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    Optional<Ingredient> findByNom(String nom);
}
