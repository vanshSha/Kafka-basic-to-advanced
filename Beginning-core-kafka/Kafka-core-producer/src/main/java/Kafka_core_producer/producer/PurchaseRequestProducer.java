package Kafka_core_producer.producer;

import Kafka_core_producer.entity.PurchaseRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
// this class use for filter duplicate purchase request
public class PurchaseRequestProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendPurchaseRequest(PurchaseRequest purchaseRequest) {
        try {
            String purchaseRequestJson = objectMapper.writeValueAsString(purchaseRequest);
            kafkaTemplate.send("t-purchase-request", purchaseRequest.getRequestNumber(), purchaseRequestJson);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
