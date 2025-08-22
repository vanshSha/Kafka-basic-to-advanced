package com.learning.command.service;

import com.learning.api.request.SubscriptionUserRequest;
import com.learning.broker.message.SubscriptionUserMessage;
import com.learning.broker.producer.SubscriptionUserProducer;
import com.learning.command.action.SubscriptionUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionUserService {

    @Autowired
    private SubscriptionUserAction action;

    public void createUser(SubscriptionUserRequest request) {
        action.publishToKafka(request);
    }
}
