package kafka_ms_pattern.broker.consumer;

import kafka_ms_pattern.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "t-commodity-order")
    public void listenOrder(OrderMessage orderMessage){
        var totalItemAmount = orderMessage.getPrice() * orderMessage.getQuantity();
        LOG.info("Processing order {}, item {}, credit card number{}, total amount {}",
                orderMessage.getOrderNumber(), orderMessage.getItemName(), orderMessage.getCreditCardNumber(), totalItemAmount);
    }
}
