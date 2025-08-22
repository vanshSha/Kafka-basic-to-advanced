package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import kafka.core.entity.PaymentRequest;
import kafka.core.entity.PurchaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class PaymentPurchaseRequest {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentPurchaseRequest.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("cachePaymentRequest")
    private Cache<String, Boolean> cachePaymentRequest;

    private boolean isExistsInCache(String key) {
        return cachePaymentRequest.getIfPresent(key) != null;
    }

    //, groupId = "group-purchase-request-all"
    @KafkaListener(topics = "t-payment-request")
    public void consumePaymentRequest(String json) {
        try {
            var paymentRequest = objectMapper.readValue(json, PaymentRequest.class);
            var cacheKey = paymentRequest.calculateHash();

            if (isExistsInCache(cacheKey)) {
                LOG.warn("Purchase request already exists in cache: {}", paymentRequest);
                return;
            }

            LOG.info("Processing purchase request: {}", paymentRequest);
            cachePaymentRequest.put(cacheKey, true);
        } catch (Exception e) {
            LOG.error("Error processing purchase request: {}", e.getMessage());
        }
    }

}
