package me.dmitriy.sushikhan.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.dmitriy.sushikhan.Sushi;
import me.dmitriy.sushikhan.SushiOrder;
import me.dmitriy.sushikhan.User;
import me.dmitriy.sushikhan.data.OrderRepository;
import me.dmitriy.sushikhan.data.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public SushiOrder patchOrder(
            @PathVariable long orderId,
            @RequestBody SushiOrder patch){
        SushiOrder order = orderRepo.findById(orderId).get();
        if(patch.getDeliveryName() != null){
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryCity() != null){
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryEntrance() != null){
            order.setDeliveryEntrance(patch.getDeliveryEntrance());
        }
        if (patch.getDeliveryFlat() != null){
            order.setDeliveryFlat(patch.getDeliveryFlat());
        }
        if (patch.getDeliveryStreet() != null){
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryHouse() != null){
            order.setDeliveryHouse(patch.getDeliveryHouse());
        }
        if (patch.getDeliveryFloor() != null){
            order.setDeliveryFloor(patch.getDeliveryFloor());
        }
        if (patch.getCcCVV() != null){
            order.setCcCVV(patch.getCcCVV());
        }
        if (patch.getCcExpiration() != null){
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcNumber() != null){
            order.setCcNumber(patch.getCcNumber());
        }
        return orderRepo.save(order);
    }

    @DeleteMapping("/orderId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }
}
