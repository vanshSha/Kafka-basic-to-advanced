package com.learning.command.action;

import com.learning.api.request.OnlineOrderRequest;
import com.learning.broker.message.OnlineOrderMessage;
import com.learning.broker.producer.OnlineOrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnlineOrderAction {

    @Autowired
    private OnlineOrderProducer producer;

    public void publishToKafka(OnlineOrderRequest request) {
        var message = new OnlineOrderMessage();

        message.setOnlineOrderNumber(request.getOnlineOrderNumber());
        message.setOrderDateTime(request.getOrderDateTime());
        message.setTotalAmount(request.getTotalAmount());
        message.setUsername(request.getUsername().toLowerCase());

        producer.publish(message);
    }
}
