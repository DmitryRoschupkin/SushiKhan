package me.dmitriy.sushikhan.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.dmitriy.sushikhan.Sushi;
import me.dmitriy.sushikhan.SushiOrder;
import me.dmitriy.sushikhan.User;
import me.dmitriy.sushikhan.data.OrderRepository;
import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("sushiOrder")
public class OrderController {

    private final UserRepository userRepository;
    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo, UserRepository userRepository) {
        this.orderRepo = orderRepo;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid SushiOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        errors.getAllErrors().forEach(System.out::println);
        if(errors.hasErrors()) {
            return "orderForm";
        }
        if(order.getSushiList() != null){
            for(Sushi sushi : order.getSushiList()){
                sushi.setSushiOrder(order);
            }
        }
        order.setUser(user);
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
