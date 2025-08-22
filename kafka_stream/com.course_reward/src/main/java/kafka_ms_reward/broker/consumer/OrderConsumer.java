package kafka_ms_reward.broker.consumer;

import kafka_ms_reward.broker.message.OrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "t-commodity-order")
    public void consumerOrder(ConsumerRecord<String, OrderMessage> consumerRecord){
        var headers = consumerRecord.headers();
        var orderMessage = consumerRecord.value();

        LOG.info("kafka headers: ");
        headers.forEach(header -> LOG.info("header {} : ", header.key(), new String(header.value())));
        LOG.info("Order: {}", orderMessage);

        var bonusPercentage = Objects.isNull(headers.lastHeader("surpriseBonus")) ? 0 :
                Integer.parseInt(new String(headers.lastHeader("surpriseBonus").value()));

        LOG.info("Surprise bonus is {}%", bonusPercentage);
    }

}
