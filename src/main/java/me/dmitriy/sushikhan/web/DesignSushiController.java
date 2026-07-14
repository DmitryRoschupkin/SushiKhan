package me.dmitriy.sushikhan.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import me.dmitriy.sushikhan.data.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignSushiController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for(Type type:types){
            model.addAttribute(type.toString().toLowerCase(),
            filterByType((List<Ingredient>) ingredients, type));
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
    public String processSushi(
            @Valid Sushi sushi, Errors errors,
            @ModelAttribute SushiOrder sushiOrder){
        if(errors.hasErrors()){
            return "design";
        }
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
