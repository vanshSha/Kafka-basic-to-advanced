package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.core.entity.FoodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(FoodOrderConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    private static final int MAX_AMOUNT_ORDER = 7;

    @KafkaListener(topics = "t-food-order", errorHandler = "myFoodOrderErrorHandler")
    public void consume(String message) {
        FoodOrder foodOrder = objectMapper.convertValue(message, FoodOrder.class);

        if (foodOrder.getAmount() > MAX_AMOUNT_ORDER) {
            LOG.error("Amount {} exceeds the maximum allowed of {} ", foodOrder.getAmount(), MAX_AMOUNT_ORDER);
            throw new IllegalArgumentException("Amount exceeds the maximum allowed limit");
        }
        LOG.info("Received food order: {}", foodOrder);
    }
}
