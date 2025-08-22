package Kafka_core_producer.producer;

import Kafka_core_producer.entity.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendInvoice(Invoice invoice) {
        try {
            String json = objectMapper.writeValueAsString(invoice);
            kafkaTemplate.send("t-invoice", (int) invoice.getAmount() % 2, invoice.getInvoiceNumber(), json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
