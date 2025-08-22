package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import kafka.core.entity.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class PaymentPurchaseRequest2 {
// this one filter message
    private static final Logger LOG = LoggerFactory.getLogger(PaymentPurchaseRequest2.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("cachePaymentRequest")
    private Cache<String, Boolean> cachePaymentRequest;


    //, groupId = "group-purchase-request-all"
    @KafkaListener(topics = "t-payment-request", containerFactory = "paymentRequestContainerFactory")
    public void consumePaymentRequest(String json) {
        try {
            var paymentRequest = objectMapper.readValue(json, PaymentRequest.class);
            var cacheKey = paymentRequest.calculateHash();



            LOG.info("Processing purchase request: {}", paymentRequest);
            cachePaymentRequest.put(cacheKey, true);
        } catch (Exception e) {
            LOG.error("Error processing purchase request: {}", e.getMessage());
        }
    }

}
