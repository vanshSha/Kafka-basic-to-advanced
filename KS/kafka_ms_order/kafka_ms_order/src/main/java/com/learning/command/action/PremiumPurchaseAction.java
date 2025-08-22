package com.learning.command.action;

import com.learning.api.request.PremiumPurchaseRequest;
import com.learning.broker.message.PremiumPurchaseMessage;
import com.learning.broker.producer.PremiumPurchaseProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PremiumPurchaseAction {


    @Autowired
    private PremiumPurchaseProducer producer;

    public void publishToKafka(PremiumPurchaseRequest request) {
        var message = new PremiumPurchaseMessage();

        message.setUsername(request.getUsername());
        message.setItem(request.getItem());
        message.setPurchaseNumber(request.getPurchaseNumber());

        producer.publish(message);
    }
}
