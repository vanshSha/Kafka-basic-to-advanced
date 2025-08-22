package Kafka_core_producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class CounterProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(int number){
        for(int a = 0; a<number; a++){
            String message = "Data " + a;
            kafkaTemplate.send("t-counter", message);
        }
    }
}
