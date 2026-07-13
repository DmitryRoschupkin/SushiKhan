package me.dmitriy.sushikhan.web;

import lombok.extern.slf4j.Slf4j;
import me.dmitriy.sushikhan.SushiOrder;
import org.springframework.stereotype.Controller;
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
    public String processOrder(SushiOrder order, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
