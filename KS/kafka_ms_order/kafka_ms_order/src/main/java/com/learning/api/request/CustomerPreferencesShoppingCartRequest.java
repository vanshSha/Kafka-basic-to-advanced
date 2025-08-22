package com.learning.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPreferencesShoppingCartRequest {

    private String customerId;
    private String itemName;
    private int cartAmount;

}
