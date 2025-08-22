package com.learning.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PremiumPurchaseRequest {

    private String item;

    private String purchaseNumber;

    private String username;

}
