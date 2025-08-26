package com.learning.broker.stream.customer.preference;

import com.learning.broker.message.CustomerPreferenceAggregateMessage;
import com.learning.broker.message.CustomerPreferenceWishlistMessage;
import org.apache.kafka.streams.kstream.Aggregator;
                                                            // interface combine multiple records into a single meaningful value
// and keep update inn real time
public class CustomerPreferenceWishlistAggregate implements Aggregator<
        String, CustomerPreferenceWishlistMessage, CustomerPreferenceAggregateMessage> {


    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferenceWishlistMessage value, CustomerPreferenceAggregateMessage aggregate) {
       aggregate.putWishlistItem(value.getItemName(), value.getWishlistDatetime());
        return aggregate;
    }
}
