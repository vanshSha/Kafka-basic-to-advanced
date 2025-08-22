package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.core.entity.Commodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class CommodityDashboardConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(CommodityDashboardConsumer.class);

    @KafkaListener(topics = "commodity-topic", groupId = "commodity-group-dashboard")
    public void listen(String message) {
        try {
            Commodity commodity = objectMapper.readValue(message, Commodity.class);
            LOG.info("Dashboard consumer: {} ", commodity);
        } catch (Exception e) {
            LOG.error("Error processing message: ",  e);
        }
    }
}
