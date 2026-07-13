package me.dmitriy.sushikhan.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import me.dmitriy.sushikhan.Ingredient;
import me.dmitriy.sushikhan.Sushi;
import me.dmitriy.sushikhan.SushiOrder;
import me.dmitriy.sushikhan.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("sushiOrder")
public class DesignSushiController {
    
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Nori", "Classical Nori", Type.SEAWEED),
                new Ingredient("Trtla", "Flour Tortilla", Type.SEAWEED),
                new Ingredient("Rice", "Rice", Type.BASE),
                new Ingredient("Cucm", "Cucumber", Type.VEGETABLE),
                new Ingredient("Maskarpone", "Maskarpone cheese", Type.CHEESE),
                new Ingredient("Sesame", "Sesame seeds", Type.TOPPING),
                new Ingredient("RedCvr", "Red Caviar", Type.TOPPING),
                new Ingredient("Salmon", "Salmon", Type.SEAFOOD),
                // BASE
                new Ingredient("RiceBr", "Brown Rice", Type.BASE),
                new Ingredient("RiceBl", "Black Rice", Type.BASE),
                new Ingredient("SushiRc", "Sushi Rice", Type.BASE),

                // SEAFOOD
                new Ingredient("Tuna", "Tuna", Type.SEAFOOD),
                new Ingredient("Shrmp", "Shrimp", Type.SEAFOOD),
                new Ingredient("Crab", "Crab Meat", Type.SEAFOOD),
                new Ingredient("Eel", "Eel", Type.SEAFOOD),

                // MEAT
                new Ingredient("Chick", "Chicken", Type.MEAT),
                new Ingredient("Beef", "Beef", Type.MEAT),
                new Ingredient("Bacon", "Bacon", Type.MEAT),
                new Ingredient("Duck", "Duck", Type.MEAT),

                // VEGETABLE
                new Ingredient("Avoc", "Avocado", Type.VEGETABLE),
                new Ingredient("Carrt", "Carrot", Type.VEGETABLE),
                new Ingredient("Onion", "Green Onion", Type.VEGETABLE),
                new Ingredient("Lettc", "Lettuce", Type.VEGETABLE),

                // CHEESE
                new Ingredient("Cream", "Cream Cheese", Type.CHEESE),
                new Ingredient("Chedd", "Cheddar", Type.CHEESE),
                new Ingredient("Mozza", "Mozzarella", Type.CHEESE),

                // SAUCE
                new Ingredient("Soy", "Soy Sauce", Type.SAUCE),
                new Ingredient("Spicy", "Spicy Mayo", Type.SAUCE),
                new Ingredient("Teriy", "Teriyaki Sauce", Type.SAUCE),
                new Ingredient("Unagi", "Unagi Sauce", Type.SAUCE),

                // TOPPING
                new Ingredient("Tobik", "Tobiko", Type.TOPPING),
                new Ingredient("Bonito", "Bonito Flakes", Type.TOPPING),
                new Ingredient("Chive", "Chives", Type.TOPPING),

                // SEAWEED
                new Ingredient("SoyPap", "Soy Paper", Type.SEAWEED),
                new Ingredient("NoriPr", "Premium Nori", Type.SEAWEED),
                new Ingredient("NoriRo", "Roasted Nori", Type.SEAWEED),

                // SEASONING
                new Ingredient("Wasbi", "Wasabi", Type.SEASONING),
                new Ingredient("Ginger", "Pickled Ginger", Type.SEASONING),
                new Ingredient("Peppr", "Black Pepper", Type.SEASONING),
                new Ingredient("Chili", "Chili Flakes", Type.SEASONING)
        );

        Type[] types = Ingredient.Type.values();
        for(Type type:types){
            model.addAttribute(type.toString().toLowerCase(),
            filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "sushiOrder")
    public SushiOrder order(){
        return new SushiOrder();
    }

    @ModelAttribute(name = "sushi")
    public Sushi sushi(){
        return new Sushi();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processSushi(Sushi sushi, @ModelAttribute SushiOrder sushiOrder){
        sushiOrder.addSushi(sushi);
        //log.info("New sushi order: {}", sushiOrder);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(
        List<Ingredient> ingredients, Type type){
            return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
