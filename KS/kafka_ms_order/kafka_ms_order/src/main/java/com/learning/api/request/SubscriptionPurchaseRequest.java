package com.learning.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPurchaseRequest {

    private String subscriptionNumber;

    private String username;

}
