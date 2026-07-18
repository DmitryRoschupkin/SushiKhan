package me.dmitriy.sushikhan.security;

import me.dmitriy.sushikhan.User;
import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepo;

    public SecurityConfig(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if(user != null) {
                return user;
            }
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        };
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/ingredients")
                        .hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(HttpMethod.DELETE, "/api/ingredients")
                        .hasAuthority("SCOPE_deleteIngredients")
                        .anyRequest().authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain uiFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/design", "/orders", "/ingredients").hasRole("USER")
                        .requestMatchers("/", "/login", "/error", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                ).oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true));
        return http.build();
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User>
        oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            Object usernameAttr = oAuth2User.getAttributes().get("username");
            if (usernameAttr == null) {
                usernameAttr = oAuth2User.getAttributes().get("sub");
            }

            if (usernameAttr == null) {
                throw new UsernameNotFoundException("Principal attributes missing username or sub field");
            }

            String username = usernameAttr.toString();
            User user = userRepo.findByUsername(username);
            if(user == null) {
                throw new UsernameNotFoundException(
                        String.format("No user found with username '%s'.", username));
            }
            return user;
        };
    }
}
