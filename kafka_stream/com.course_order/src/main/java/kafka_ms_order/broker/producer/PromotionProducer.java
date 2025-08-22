package kafka_ms_order.broker.producer;

import kafka_ms_order.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PromotionProducer {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionProducer.class);

    @Autowired
    private KafkaTemplate<String, PromotionMessage> kafkaTemplate;

    public void sendPromotion(PromotionMessage promotionMessage) {
        try {
            var sendResult = kafkaTemplate.send("t-commodity-promotion", promotionMessage.getPromotionCode(), promotionMessage)
                    .get(3, TimeUnit.SECONDS);
            LOG.info("Promotion sent successfully, promotion code {}, promotion message {}",
                    sendResult.getProducerRecord().value());
        } catch (Exception e) {
            LOG.error("Error sending promotion {}", promotionMessage.getPromotionCode(), e);
        }
        LOG.info("Just a dummy message for a promotion {}", promotionMessage.getPromotionCode());
    }
}