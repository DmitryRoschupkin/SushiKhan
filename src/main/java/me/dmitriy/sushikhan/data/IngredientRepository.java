package me.dmitriy.sushikhan.data;

import me.dmitriy.sushikhan.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
