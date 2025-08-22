package com.learning.broker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionOfferMessage {

    private String username;
    private String duration;
    private String subscriptionNumber;


}
