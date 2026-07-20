package me.dmitriy.sushikhan.web;


import me.dmitriy.sushikhan.Ingredient;
import me.dmitriy.sushikhan.data.IngredientRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/ingredients")
public class IngredientViewController {

    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final IngredientRepository ingredientRepository;

    public IngredientViewController(OAuth2AuthorizedClientManager authorizedClientManager, IngredientRepository ingredientRepository) {
        this.authorizedClientManager = authorizedClientManager;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping()
    public String ingredientsPage(Model model){
        model.addAttribute("types", Ingredient.Type.values());
        model.addAttribute("accessToken", getSystemToken());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "ingredients-list";
    }

    @GetMapping("/admin/edit")
    public String adminPanel(Model model) {
        model.addAttribute("ingredients", ingredientRepository.findAll());
        model.addAttribute("accessToken", getSystemToken());
        model.addAttribute("types", Ingredient.Type.values());
        return "ingredients-admin";
    }

    private String getSystemToken() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("sushi-admin-client")
                .principal("sushi-admin-client")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        return (authorizedClient != null) ? authorizedClient.getAccessToken().getTokenValue() : "";
    }
}
