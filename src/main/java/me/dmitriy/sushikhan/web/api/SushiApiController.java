package me.dmitriy.sushikhan.web.api;

import me.dmitriy.sushikhan.Ingredient;
import me.dmitriy.sushikhan.Sushi;
import me.dmitriy.sushikhan.data.IngredientRepository;
import me.dmitriy.sushikhan.data.SushiRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/sushi", produces = "application/json")
@CrossOrigin(origins = "https://localhost:8443")
public class SushiApiController {

    private final SushiRepository sushiRepository;
    private final IngredientRepository ingredientRepository;

    public SushiApiController(SushiRepository sushiRepository,
                              IngredientRepository ingredientRepository) {
        this.sushiRepository = sushiRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable String id) {
        return ingredientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recent")
    public List<Sushi> getRecentSushi() {
        PageRequest page = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        return sushiRepository.findAll(page).getContent();
    }
}