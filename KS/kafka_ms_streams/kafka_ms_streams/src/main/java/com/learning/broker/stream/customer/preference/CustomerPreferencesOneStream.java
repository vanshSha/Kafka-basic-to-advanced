package com.learning.broker.stream.customer.preference;

import com.learning.broker.message.CustomerPreferenceAggregateMessage;
import com.learning.broker.message.CustomerPreferenceShoppingCartMessage;
import com.learning.broker.message.CustomerPreferenceWishlistMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

//@Component
public class  CustomerPreferencesOneStream {

    private static final CustomerPreferenceShoppingCartAggregator SHOPPING_CART_AGGREGATOR = new CustomerPreferenceShoppingCartAggregator();

    private static final CustomerPreferenceWishlistAggregate WISHLIST_AGGREGATOR = new CustomerPreferenceWishlistAggregate();

    @Autowired
    void kStreamCustomerPreference(StreamsBuilder streamsBuilder){
        var stringSerde = Serdes.String();

        var shoppingCartSerde = new JsonSerde<>(CustomerPreferenceShoppingCartMessage.class);

        var wishlistSerde = new JsonSerde<>(CustomerPreferenceWishlistMessage.class);

        var aggregateSerde = new JsonSerde<>(CustomerPreferenceAggregateMessage.class);

        var groupedShoppingCartStream = streamsBuilder.stream("t-commodity-customer-preference-shopping-cart",
                Consumed.with(stringSerde, shoppingCartSerde)).groupByKey();// I have data and with key . i will group data by
        // a key

        var groupedWishlistStream = streamsBuilder.stream("t-commodity-customer-preference-wishlist",
                Consumed.with(stringSerde, wishlistSerde)).groupByKey();

        var customerPreferenceStream = groupedShoppingCartStream
                .cogroup(SHOPPING_CART_AGGREGATOR)
                .cogroup(groupedWishlistStream, WISHLIST_AGGREGATOR)
                .aggregate(
                        () -> new CustomerPreferenceAggregateMessage(),
                        Materialized.with(stringSerde, aggregateSerde) // how to store in aggregate
                ).toStream();
        customerPreferenceStream.to("t-commodity-customer-preferences-all",
                Produced.with(stringSerde, aggregateSerde));

    }
}
