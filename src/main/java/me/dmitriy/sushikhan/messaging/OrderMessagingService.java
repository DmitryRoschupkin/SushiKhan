package me.dmitriy.sushikhan.messaging;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

public interface OrderMessagingService<T> {
    void sendOrder(T t) throws JMSException;
}
