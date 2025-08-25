package com.learning.broker.stream.customer.purchase;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class CustomerPurchaseTwoStream {
    @Autowired
    void kStreamCustomerPurchase(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var topic = List.of("t-commodity-customer-purchase-mobile", "t-commodity-customer-purchase-web");
        builder.stream(topic, Consumed.with(stringSerde, stringSerde)).to("t-commodity-customer-purchase-all");
    }
}
