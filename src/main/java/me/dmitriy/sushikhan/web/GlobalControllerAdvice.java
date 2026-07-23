package me.dmitriy.sushikhan.web;


import me.dmitriy.sushikhan.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("currentUser")
    public User getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }
}
