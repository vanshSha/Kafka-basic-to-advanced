package Kafka_core_producer.producer;

import Kafka_core_producer.entity.PaymentRequest;
import Kafka_core_producer.entity.PurchaseRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
// this class is alternative approach to filter duplicate purchase requests
public class PaymentRequestProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendPaymentRequest(PaymentRequest paymentRequest) {
        try {
            String paymentRequestJson = objectMapper.writeValueAsString(paymentRequest);
            kafkaTemplate.send("t-payment-request", paymentRequestJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
