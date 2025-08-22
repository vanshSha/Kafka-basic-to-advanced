package com.learning.command.service;

import com.learning.api.request.PremiumPurchaseRequest;
import com.learning.command.action.PremiumPurchaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremiumPurchaseService {

    @Autowired
    private PremiumPurchaseAction action;

    public void createPurchase(PremiumPurchaseRequest request) {
        action.publishToKafka(request);
    }
}
