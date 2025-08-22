package kafka_ms_order.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderReplyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderReplyConsumer.class);

    @KafkaListener(topics = "t-commodity-order-reply")
    public void consumerOrderReply(String message){
        LOG.info("Consumed order reply message: {}", message);
    }
}
