package me.dmitriy.sushikhan.web;


import me.dmitriy.sushikhan.Ingredient;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredients")
public class IngredientViewController {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public IngredientViewController(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @GetMapping()
    public String ingredientsPage(Model model){
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("sushi-admin-client")
                .principal("sushi-admin-client") // используем имя клиента как principal
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
        String tokenValue = "";
        if (authorizedClient != null) {
            tokenValue = authorizedClient.getAccessToken().getTokenValue();
        }
        model.addAttribute("types", Ingredient.Type.values());
        model.addAttribute("accessToken", tokenValue);
        return "ingredients-list";
    }
}
