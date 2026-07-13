package me.dmitriy.sushikhan.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.dmitriy.sushikhan.SushiOrder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("sushiOrder")
public class OrderController {
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid SushiOrder order,
                               Errors errors,
                               SessionStatus sessionStatus) {
        if(errors.hasErrors()) {
            return "orderForm";
        }
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
