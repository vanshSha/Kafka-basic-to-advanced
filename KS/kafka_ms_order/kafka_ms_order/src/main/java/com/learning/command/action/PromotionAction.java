package com.learning.command.action;

import com.learning.api.request.PromotionRequest;
import com.learning.broker.message.PromotionMessage;
import com.learning.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionAction {

    @Autowired
    private PromotionProducer promotionProducer;

    public PromotionMessage convertToPromotionMessage(PromotionRequest request) {
        return new PromotionMessage(request.getPromotionCode());
    }

    public void sendToKafka(PromotionMessage promotionMessage){
        promotionProducer.sendPromotion(promotionMessage);
    }
}