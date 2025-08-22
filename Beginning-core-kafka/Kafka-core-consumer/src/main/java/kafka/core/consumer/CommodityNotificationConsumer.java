package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.core.entity.Commodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//@Service
public class CommodityNotificationConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(CommodityNotificationConsumer.class);

    @KafkaListener(topics = "t-commodity", groupId = "commodity-group-notification")
    public void listen(String message) {
        try {
            Commodity commodity = objectMapper.readValue(message, Commodity.class);
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5, 10)); // Simulate processing delay
            LOG.info("Notification consumer: {} ", commodity);
        } catch (Exception e) {
            LOG.error("Error processing message: ",  e);
        }
    }
}
