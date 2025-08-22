package Kafka_core_producer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class FixedRateAIProducer {

    private static final Logger LOG = LoggerFactory.getLogger(FixedRateAIProducer.class);
    private static final String TOPIC = "t-fixedrate";
    private int counter = 0;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 1000) // 1 second
    public void sendMessage() {
        counter++;
        String message = "Message number " + counter;
        kafkaTemplate.send(TOPIC, message);
        LOG.info("Sent message: "+ message + " to topic: " + TOPIC);

    }
}
