package kafka.core.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.core.entity.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class InvoiceConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t-invoice", concurrency = "2", containerFactory = "invoiceDltContainerFactory")
    public void consume(String message) throws Exception {
        var invoice = objectMapper.readValue(message, Invoice.class);

        if(invoice.getAmount() < 1){
            throw new IllegalArgumentException("Invalid amount :" + invoice.getAmount());
        }
        LOG.info("Consumed invoice : {}", invoice);
    }
}
