package com.learning.broker.producer;

import com.learning.broker.message.OnlinePaymentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentProducer {

    @Autowired
    private KafkaTemplate<String, OnlinePaymentMessage> kafkaTemplate;

    public void publish(OnlinePaymentMessage message) {
        kafkaTemplate.send("t-commodity-online-payment", null, message.getOnlineOrderNumber(), message);
    }
}
