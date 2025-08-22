package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import kafka.core.entity.PurchaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class PurchaseRequestConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("cachePurchaseRequest")
    private Cache<String, Boolean> cachePurchaseRequest;

    private boolean isExistsInCache(String requestNumber) {
        return cachePurchaseRequest.getIfPresent(requestNumber) != null;
    }

//, groupId = "group-purchase-request-all"
    @KafkaListener(topics = "t-purchase-request")
    public void consumePurchaseRequest(String json) {
        try {
            var purchaseRequest = objectMapper.readValue(json, PurchaseRequest.class);

            if (isExistsInCache(purchaseRequest.getRequestNumber())) {
                LOG.warn("Purchase request already exists in cache: {}", purchaseRequest.getRequestNumber());
                return;
            }

            LOG.info("Processing purchase request: {}", purchaseRequest.getRequestNumber());
            cachePurchaseRequest.put(purchaseRequest.getRequestNumber(), true);
        } catch (Exception e) {
            LOG.error("Error processing purchase request: {}", e.getMessage());
        }
    }

}
