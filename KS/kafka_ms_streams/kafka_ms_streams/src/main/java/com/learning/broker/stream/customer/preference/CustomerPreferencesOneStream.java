package com.learning.broker.stream.customer.preference;

import com.learning.broker.message.CustomerPreferenceAggregateMessage;
import com.learning.broker.message.CustomerPreferencesShoppingCartMessage;
import com.learning.broker.message.CustomerPreferencesWishlistMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

//@Component
public class CustomerPreferencesOneStream {

    private static final CustomerPreferenceShoppingCartAggregator SHOPPING_CART_AGGREGATOR = new CustomerPreferenceShoppingCartAggregator();

    private static final CustomerPreferencesWishlistAggregate WISHLIST_AGGREGATOR = new CustomerPreferencesWishlistAggregate();

    @Autowired
    void kstreamCustomerPreference(StreamsBuilder streamsBuilder){
        var stringSerde = Serdes.String();
        var shoppingCartSerde = new JsonSerde<>(CustomerPreferencesShoppingCartMessage.class);
        var wishlistSerde = new JsonSerde<>(CustomerPreferencesWishlistMessage.class);
        var aggregateSerde = new JsonSerde<>(CustomerPreferenceAggregateMessage.class);
        var groupedShoppingCartStream = streamsBuilder.stream("t-commodity-preference-shopping-cart",
                Consumed.with(stringSerde, shoppingCartSerde)).groupByKey();

        var groupedWishlistStream = streamsBuilder.stream("t-commodity-preference-wishlist",
                Consumed.with(stringSerde, wishlistSerde)).groupByKey();

        var customerPreferenceStream = groupedShoppingCartStream
                .cogroup(SHOPPING_CART_AGGREGATOR)
                .cogroup(groupedWishlistStream, WISHLIST_AGGREGATOR)
                .aggregate(
                        () -> new CustomerPreferenceAggregateMessage(),
                        Materialized.with(stringSerde, aggregateSerde)
                ).toStream();
        customerPreferenceStream.to("t-commodity-customer-preferences-all",
                Produced.with(stringSerde, aggregateSerde));

    }
}
