package me.dmitriy.sushikhan;

import lombok.Data;

@Data
public class IngredientRef {
    private final String ingredient;

    public String getIngredient() {
        return ingredient;
    }
}
