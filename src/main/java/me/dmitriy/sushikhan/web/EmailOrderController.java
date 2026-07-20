package me.dmitriy.sushikhan.web;


import lombok.RequiredArgsConstructor;
import me.dmitriy.sushikhan.data.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/email-orders")
@RequiredArgsConstructor
public class EmailOrderController {

    private final OrderRepository orderRepository;
    @GetMapping
    @Transactional(readOnly = true)
    public String showEmailOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAllWithSushis());
        return "email-orders";
    }
}
