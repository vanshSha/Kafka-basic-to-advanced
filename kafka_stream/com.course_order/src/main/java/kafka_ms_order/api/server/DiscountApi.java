package kafka_ms_order.api.server;

import Kafka_core_course.broker.message.DiscountMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discount")
public class DiscountApi {

    @PostMapping
    public DiscountMessage createDiscount(@RequestBody DiscountMessage discountMessage) {
        // Here you can process the discount or send to Kafka etc.
        System.out.println("Received Discount: " + discountMessage);
        return discountMessage; // return back as confirmation
    }
}
