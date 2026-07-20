package me.dmitriy.sushikhan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredients/admin")
public class DashboardController {

    @GetMapping
    public String showDashboard() {
        return "sushi-dashboard";
    }
}