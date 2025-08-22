package kafka.core.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class FixedRateAIConsumer2 {

    @KafkaListener(topics = "t-fixedrate-1")
    public void consume(String message) {
        System.out.println("Received message : " + message);
    }
}
