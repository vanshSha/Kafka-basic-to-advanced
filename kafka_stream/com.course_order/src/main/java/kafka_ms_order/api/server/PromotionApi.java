package kafka_ms_order.api.server;

import kafka_ms_order.api.request.PromotionRequest;
import kafka_ms_order.command.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromotionApi {

    @Autowired
    private PromotionService promotionService;

    @PostMapping(value = "api/promotion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createPromotion(@RequestBody PromotionRequest promotionRequest){
        promotionService.createPromotion(promotionRequest);
        return new ResponseEntity<>(promotionRequest.getPromotionCode(), HttpStatus.CREATED);
    }
}
