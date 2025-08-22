package com.learning.broker.stream.customer.preference;

import com.learning.broker.message.CustomerPreferenceAggregateMessage;
import com.learning.broker.message.CustomerPreferencesWishlistMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferencesWishlistAggregate implements Aggregator<
String, CustomerPreferencesWishlistMessage, CustomerPreferenceAggregateMessage> {
    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferencesWishlistMessage value, CustomerPreferenceAggregateMessage aggregate) {
        aggregate.putWishlistItem(value.getItemName(), value.getWishlistDatetime());
        return aggregate;
    }
}
