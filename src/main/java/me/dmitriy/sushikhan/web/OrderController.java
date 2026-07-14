package me.dmitriy.sushikhan.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.dmitriy.sushikhan.SushiOrder;
import me.dmitriy.sushikhan.data.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("sushiOrder")
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid SushiOrder order,
                               Errors errors,
                               SessionStatus sessionStatus) {
        errors.getAllErrors().forEach(System.out::println);
        if(errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
