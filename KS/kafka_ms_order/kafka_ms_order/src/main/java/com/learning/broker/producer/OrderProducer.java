package com.learning.broker.producer;

import com.learning.broker.message.OrderMessage;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    public void sendOrder(OrderMessage orderMessage) {

         var producerRecord = buildProducerRecord(orderMessage);
        kafkaTemplate.send(producerRecord)
                .whenComplete((
                        recordMetadata, ex) -> {
                    if (ex == null) {
                        LOG.info("Order {} sent successfully", orderMessage.getOrderNumber());
                    } else {
                        LOG.error("Failed to send order {}", orderMessage.getOrderNumber(), ex);
                    }
                });
        LOG.info("Just a dummy message for food order {}, item {}", orderMessage.getItemName());
    }

    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage orderMessage) {
        var surpriseBonus = StringUtils.startsWithIgnoreCase(orderMessage.getOrderLocation(), "A") ? 25 : 15;
        var kafkaHeaders = new ArrayList<Header>(); // we put custom metadata . Header carry one key and value.
        var surpriseBonusHeader = new RecordHeader("surprise-bonus", String.valueOf(surpriseBonus).getBytes());
        kafkaHeaders.add(surpriseBonusHeader);  // header accepts only byte . 25 int -> String then covert into byte
        return new ProducerRecord<>("t-commodity-order", null, orderMessage.getOrderNumber(), orderMessage, kafkaHeaders);
    }


}
