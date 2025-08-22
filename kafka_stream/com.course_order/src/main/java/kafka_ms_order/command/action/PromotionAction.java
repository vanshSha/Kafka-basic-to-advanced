package kafka_ms_order.command.action;

import kafka_ms_order.api.request.PromotionRequest;
import kafka_ms_order.broker.message.PromotionMessage;
import kafka_ms_order.broker.producer.PromotionProducer;
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