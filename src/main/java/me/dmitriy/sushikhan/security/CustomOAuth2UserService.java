package me.dmitriy.sushikhan.security;

import me.dmitriy.sushikhan.User;
import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Object rolesClaim = oAuth2User.getAttributes().get("roles");
        String extractedRole = "ROLE_USER";

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.addAll(oAuth2User.getAuthorities());

        if (rolesClaim instanceof List<?> rolesList) {
            if (!rolesList.isEmpty()) {
                extractedRole = rolesList.get(0).toString();
                authorities.add(new SimpleGrantedAuthority(extractedRole));
            }
        }

        String username = oAuth2User.getAttribute("sub");

        if (username == null) {
            username = oAuth2User.getName();
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
        }
        user.setRole(extractedRole);
        userRepository.save(user);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println("LIST OF RIGHTS IN THE SESSION: " + authorities);
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "sub");    }
}
