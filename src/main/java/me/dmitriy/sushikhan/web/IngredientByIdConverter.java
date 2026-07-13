package me.dmitriy.sushikhan.web;

import me.dmitriy.sushikhan.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private Map<String, Ingredient> ingredientMap = new HashMap<>();
    public IngredientByIdConverter() {
        ingredientMap.put("Nori", new Ingredient("Nori", "Classical Nori", Ingredient.Type.SEAWEED));
        ingredientMap.put("Trtla", new Ingredient("Trtla", "Flour Tortilla", Ingredient.Type.SEAWEED));
        ingredientMap.put("Rice", new Ingredient("Rice", "Rice", Ingredient.Type.BASE));
        ingredientMap.put("Cucm", new Ingredient("Cucm", "Cucumber", Ingredient.Type.VEGETABLE));
        ingredientMap.put("Maskarpone", new Ingredient("Maskarpone", "Maskarpone cheese", Ingredient.Type.CHEESE));
        ingredientMap.put("Sesame", new Ingredient("Sesame", "Sesame seeds", Ingredient.Type.TOPPING));
        ingredientMap.put("RedCvr", new Ingredient("RedCvr", "Red Caviar", Ingredient.Type.TOPPING));
        ingredientMap.put("Salmon", new Ingredient("Salmon", "Salmon", Ingredient.Type.SEAFOOD));

        ingredientMap.put("RiceBr", new Ingredient("RiceBr", "Brown Rice", Ingredient.Type.BASE));
        ingredientMap.put("RiceBl", new Ingredient("RiceBl", "Black Rice", Ingredient.Type.BASE));
        ingredientMap.put("SushiRc", new Ingredient("SushiRc", "Sushi Rice", Ingredient.Type.BASE));

        ingredientMap.put("Tuna", new Ingredient("Tuna", "Tuna", Ingredient.Type.SEAFOOD));
        ingredientMap.put("Shrmp", new Ingredient("Shrmp", "Shrimp", Ingredient.Type.SEAFOOD));
        ingredientMap.put("Crab", new Ingredient("Crab", "Crab Meat", Ingredient.Type.SEAFOOD));
        ingredientMap.put("Eel", new Ingredient("Eel", "Eel", Ingredient.Type.SEAFOOD));

        ingredientMap.put("Chick", new Ingredient("Chick", "Chicken", Ingredient.Type.MEAT));
        ingredientMap.put("Beef", new Ingredient("Beef", "Beef", Ingredient.Type.MEAT));
        ingredientMap.put("Bacon", new Ingredient("Bacon", "Bacon", Ingredient.Type.MEAT));
        ingredientMap.put("Duck", new Ingredient("Duck", "Duck", Ingredient.Type.MEAT));

        ingredientMap.put("Avoc", new Ingredient("Avoc", "Avocado", Ingredient.Type.VEGETABLE));
        ingredientMap.put("Carrt", new Ingredient("Carrt", "Carrot", Ingredient.Type.VEGETABLE));
        ingredientMap.put("Onion", new Ingredient("Onion", "Green Onion", Ingredient.Type.VEGETABLE));
        ingredientMap.put("Lettc", new Ingredient("Lettc", "Lettuce", Ingredient.Type.VEGETABLE));

        ingredientMap.put("Cream", new Ingredient("Cream", "Cream Cheese", Ingredient.Type.CHEESE));
        ingredientMap.put("Chedd", new Ingredient("Chedd", "Cheddar", Ingredient.Type.CHEESE));
        ingredientMap.put("Mozza", new Ingredient("Mozza", "Mozzarella", Ingredient.Type.CHEESE));

        ingredientMap.put("Soy", new Ingredient("Soy", "Soy Sauce", Ingredient.Type.SAUCE));
        ingredientMap.put("Spicy", new Ingredient("Spicy", "Spicy Mayo", Ingredient.Type.SAUCE));
        ingredientMap.put("Teriy", new Ingredient("Teriy", "Teriyaki Sauce", Ingredient.Type.SAUCE));
        ingredientMap.put("Unagi", new Ingredient("Unagi", "Unagi Sauce", Ingredient.Type.SAUCE));

        ingredientMap.put("Tobik", new Ingredient("Tobik", "Tobiko", Ingredient.Type.TOPPING));
        ingredientMap.put("Bonito", new Ingredient("Bonito", "Bonito Flakes", Ingredient.Type.TOPPING));
        ingredientMap.put("Chive", new Ingredient("Chive", "Chives", Ingredient.Type.TOPPING));

        ingredientMap.put("SoyPap", new Ingredient("SoyPap", "Soy Paper", Ingredient.Type.SEAWEED));
        ingredientMap.put("NoriPr", new Ingredient("NoriPr", "Premium Nori", Ingredient.Type.SEAWEED));
        ingredientMap.put("NoriRo", new Ingredient("NoriRo", "Roasted Nori", Ingredient.Type.SEAWEED));

        ingredientMap.put("Wasbi", new Ingredient("Wasbi", "Wasabi", Ingredient.Type.SEASONING));
        ingredientMap.put("Ginger", new Ingredient("Ginger", "Pickled Ginger", Ingredient.Type.SEASONING));
        ingredientMap.put("Peppr", new Ingredient("Peppr", "Black Pepper", Ingredient.Type.SEASONING));
        ingredientMap.put("Chili", new Ingredient("Chili", "Chili Flakes", Ingredient.Type.SEASONING));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
