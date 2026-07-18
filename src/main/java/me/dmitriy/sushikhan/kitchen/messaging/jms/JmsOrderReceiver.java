package me.dmitriy.sushikhan.kitchen.messaging.jms;

import jakarta.jms.JMSException;
import me.dmitriy.sushikhan.SushiOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsOrderReceiver implements OrderReseiver {

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public SushiOrder receiveOrder() throws JMSException {
        return (SushiOrder) jmsTemplate.receiveAndConvert("sushikhan.order.queue");
    }
}
