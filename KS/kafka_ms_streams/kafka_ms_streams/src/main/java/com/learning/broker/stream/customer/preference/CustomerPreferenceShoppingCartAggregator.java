package com.learning.broker.stream.customer.preference;

import com.learning.broker.message.CustomerPreferenceAggregateMessage;
import com.learning.broker.message.CustomerPreferencesShoppingCartMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceShoppingCartAggregator implements Aggregator<String, CustomerPreferencesShoppingCartMessage, CustomerPreferenceAggregateMessage> {
    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferencesShoppingCartMessage value, CustomerPreferenceAggregateMessage aggregate) {
        {
            aggregate.putShoppingCarItem(value.getItemName(), value.getCartDatetime());

            return aggregate;
        }
    }
}
