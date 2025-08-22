package kafka.core.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//@Service
public class CounterConsumer {

    @KafkaListener(topics = "t-counter", groupId = "counter-group-fast")
    public void consumeFast(String message){
        System.out.println("Fast: " + message);
    }

    @KafkaListener(topics = "t-counter", groupId = "counter-group-slow")
    public void consumeSlow(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5); // Simulate processing delay
        System.out.println("Slow: " + message);
    }
}
