package Kafka_core_course.broker.consumer;

import Kafka_core_course.broker.message.DiscountMessage;
import Kafka_core_course.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "t-commodity-promotion")
public class PromotionConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionConsumer.class);

    @KafkaHandler
    public void listenPromotion(PromotionMessage promotionMessage){
        LOG.info("Processing promotion: {}", promotionMessage);
    }

    @KafkaHandler
    public void listenDiscount(DiscountMessage discountMessage){
        LOG.info("Processing discount: {}", discountMessage);
    }

    // This method handles raw String messages (fallback)
    @KafkaHandler(isDefault = true)
    public void listenRaw(String message){
        LOG.warn("Received raw message: {}", message);
    }
}
