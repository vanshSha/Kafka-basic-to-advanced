package com.learning.api.server;


import com.learning.broker.message.DiscountMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
