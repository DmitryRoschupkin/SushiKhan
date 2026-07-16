package me.dmitriy.sushikhan.service;

import me.dmitriy.sushikhan.Ingredient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IngredientClient {

    private final RestTemplate restTemplate;
    public IngredientClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Ingredient getIngredientById(String id) {
        return restTemplate.getForObject("https://localhost:8443/ingredients/{id}"
                + id, Ingredient.class);
    }

    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("https://localhost:8443/ingredients/{id}"
                + ingredient.getId(), ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        restTemplate.delete("https://localhost:8443/ingredients/{id}"
                + ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("https://localhost:8443/ingredients",
                ingredient, Ingredient.class);
    }
}
