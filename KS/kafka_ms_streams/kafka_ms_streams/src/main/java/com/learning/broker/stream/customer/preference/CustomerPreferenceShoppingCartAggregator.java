package com.learning.broker.stream.customer.preference;

import com.learning.broker.message.CustomerPreferenceAggregateMessage;
import com.learning.broker.message.CustomerPreferenceShoppingCartMessage;
//import com.learning.broker.message.CustomerShoppingWishlistMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceShoppingCartAggregator implements Aggregator<String, CustomerPreferenceShoppingCartMessage, CustomerPreferenceAggregateMessage> {


    @Override
    public CustomerPreferenceAggregateMessage apply(String key,
                                                    CustomerPreferenceShoppingCartMessage value,
                                                    CustomerPreferenceAggregateMessage aggregate)
    {
        aggregate.putShoppingCarItem(value.getItemName(), value.getCartDatetime());
        return  aggregate;
    }
}
