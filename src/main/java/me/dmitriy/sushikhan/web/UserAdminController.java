package me.dmitriy.sushikhan.web;

import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/admin")
public class UserAdminController {

    private final UserRepository userRepository;
    public UserAdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-admin-control";
    }
}
