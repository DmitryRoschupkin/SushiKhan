package me.dmitriy.sushikhan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Sushi {

    @NotBlank
    @Size(min = 1, message = "Name must be at least 1 character long!")
    private String name;

    @NotNull
    @Size(min = 1, message = "You have to choose at least 1 ingredient!")
    private List<Ingredient> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
