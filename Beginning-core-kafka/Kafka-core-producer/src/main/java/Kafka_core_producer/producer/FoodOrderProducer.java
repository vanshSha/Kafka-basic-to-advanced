package Kafka_core_producer.producer;

import Kafka_core_producer.entity.FoodOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendFoodOrder(FoodOrder foodOrder) {
        try {
            var json = objectMapper.writeValueAsString(foodOrder);
            kafkaTemplate.send("t-food-order", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
