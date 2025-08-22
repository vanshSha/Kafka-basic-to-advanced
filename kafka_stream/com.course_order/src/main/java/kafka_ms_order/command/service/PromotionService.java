package kafka_ms_order.command.service;

import kafka_ms_order.api.request.PromotionRequest;
import kafka_ms_order.command.action.PromotionAction;
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
