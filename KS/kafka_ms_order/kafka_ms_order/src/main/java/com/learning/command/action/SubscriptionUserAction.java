package com.learning.command.action;

import com.learning.api.request.SubscriptionUserRequest;
import com.learning.broker.message.SubscriptionUserMessage;
import com.learning.broker.producer.SubscriptionUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionUserAction {

    @Autowired
    private SubscriptionUserProducer producer;

    public void publishToKafka(SubscriptionUserRequest request) {
        var message = new SubscriptionUserMessage();

        message.setDuration(request.getDuration());
        message.setUsername(request.getUsername());

        producer.publish(message);
    }
}