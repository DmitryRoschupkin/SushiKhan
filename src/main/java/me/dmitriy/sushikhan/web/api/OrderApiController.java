package me.dmitriy.sushikhan.web.api;

import jakarta.jms.JMSException;
import me.dmitriy.sushikhan.SushiOrder;
import me.dmitriy.sushikhan.data.OrderRepository;
import me.dmitriy.sushikhan.messaging.OrderMessagingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders",
        produces = "application/json")
@CrossOrigin(origins = "https://localhost:8443")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderMessagingService<SushiOrder> messageService;

    public OrderApiController(OrderRepository orderRepository,
                              OrderMessagingService<SushiOrder> messageService) {
        this.orderRepository = orderRepository;
        this.messageService = messageService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SushiOrder postOrder(@RequestBody SushiOrder sushiOrder) throws JMSException {
        messageService.sendOrder(sushiOrder);
        return orderRepository.save(sushiOrder);
    }
}
