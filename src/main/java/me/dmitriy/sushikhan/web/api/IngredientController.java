package me.dmitriy.sushikhan.web.api;


import me.dmitriy.sushikhan.Ingredient;
import me.dmitriy.sushikhan.data.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/ingredients", produces="application/json")
@CrossOrigin(origins = "https://localhost:8443")
public class IngredientController {

    private IngredientRepository repo;

    @Autowired
    public IngredientController(IngredientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('ADMIN')}")
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return repo.save(ingredient);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#{hasRole('ADMIN')}")
    public void deleteIngredient(@PathVariable String id) {
        repo.deleteById(id);
    }
}
