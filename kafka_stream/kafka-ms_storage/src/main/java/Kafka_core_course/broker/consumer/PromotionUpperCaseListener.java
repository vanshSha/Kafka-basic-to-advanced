package Kafka_core_course.broker.consumer;

import Kafka_core_course.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PromotionUpperCaseListener {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionUpperCaseListener.class);

    @KafkaListener(topics = "t-commodity-promotion-uppercase")
    public void listenPromotion(PromotionMessage message){
        LOG.info("Processing uppercase promotion: {}", message);

    }
}
