package com.learning.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPurchaseMobileRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {

        private double latitude;

        private double longitude;
    }

    private int purchaseAmount;

    private String mobileAppVersion;

    private String operatingSystem;

    private Location location;
}
