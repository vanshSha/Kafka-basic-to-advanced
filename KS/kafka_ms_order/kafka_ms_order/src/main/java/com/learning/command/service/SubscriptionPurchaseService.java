package com.learning.command.service;

import com.learning.api.request.SubscriptionPurchaseRequest;
import com.learning.command.action.SubscriptionPurchaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPurchaseService {

    @Autowired
    private SubscriptionPurchaseAction action;

    public void createPurchase(SubscriptionPurchaseRequest request){
        action.publishToKafka(request);

    }
}
