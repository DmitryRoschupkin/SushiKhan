package me.dmitriy.sushikhan.messaging;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import me.dmitriy.sushikhan.SushiOrder;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderMessagingService implements OrderMessagingService<SushiOrder> {

    private final JmsTemplate jmsTemplate;
    private final Destination orderQueue;
    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate, Destination orderQueue) {
        this.jmsTemplate = jmsTemplate;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(SushiOrder sushiOrder) {
        jmsTemplate.convertAndSend(orderQueue
                ,sushiOrder
                ,message -> {
                    message.setStringProperty("X_ORDER_SOURCE", "WEB");
                    return message;
                });
    }

}
