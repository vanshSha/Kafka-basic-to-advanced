package com.learning.command.service;

import com.learning.api.request.PromotionRequest;
import com.learning.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    @Autowired
    private PromotionAction promotionAction;

    public void createPromotion(PromotionRequest promotionRequest){
        var message = promotionAction.convertToPromotionMessage(promotionRequest);
        promotionAction.sendToKafka(message);
    }
}
