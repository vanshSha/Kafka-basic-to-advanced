package com.learning.command.action;

import com.learning.api.request.SubscriptionPurchaseRequest;
import com.learning.broker.message.SubscriptionPurchaseMessage;
import com.learning.broker.producer.SubscriptionPurchaseProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPurchaseAction {

    @Autowired
    private SubscriptionPurchaseProducer producer;

    public void publishToKafka(SubscriptionPurchaseRequest request){
        var message = new SubscriptionPurchaseMessage();

        message.setSubscriptionNumber(request.getSubscriptionNumber());
        message.setUsername(request.getUsername());

        producer.publish(message);
    }
}
