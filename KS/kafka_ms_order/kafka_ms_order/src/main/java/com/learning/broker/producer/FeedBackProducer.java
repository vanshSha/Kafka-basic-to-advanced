package com.learning.broker.producer;

import com.learning.broker.message.FeedBackMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FeedBackProducer {

    @Autowired
    private KafkaTemplate<String, FeedBackMessage> kafkaTemplate;

    public void publish(FeedBackMessage message) {
        kafkaTemplate.send("t-commodity-feedback", message);
    }

}
