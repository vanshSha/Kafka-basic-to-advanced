package com.learning.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineOrderRequest {

    //@JsonProperty("online_order_number")
    private String onlineOrderNumber;

    //@JsonProperty("order_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private OffsetDateTime orderDateTime;

    //@JsonProperty("total_amount")
    private int totalAmount;

   // @JsonProperty("user_name")
    private String username;

}
