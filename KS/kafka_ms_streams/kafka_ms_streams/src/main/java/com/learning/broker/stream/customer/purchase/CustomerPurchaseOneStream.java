package com.learning.broker.stream.customer.purchase;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class CustomerPurchaseOneStream {
    @Autowired
    void kStreamCustomerPurchase(StreamsBuilder builder) {
        var stringSerde = Serdes.String();

        var customerPurchaseMobileStream = builder.stream("t-commodity-customer-purchase-mobile",
                Consumed.with(stringSerde, stringSerde));
        var customerPurchaseWebStream = builder.stream("t-commodity-customer-purchase-web",
                Consumed.with(stringSerde, stringSerde));
        // merge basically used for convert 2 stream into 1 stream.
        customerPurchaseMobileStream.merge(customerPurchaseWebStream).to("t-commodity-customer-purchase-all");
    }
}
