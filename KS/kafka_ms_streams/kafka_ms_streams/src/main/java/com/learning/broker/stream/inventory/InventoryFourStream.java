package com.learning.broker.stream.inventory;

import com.learning.broker.message.InventoryMessage;
import com.learning.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

//@Component
public class InventoryFourStream {

    @Autowired
    void kstreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var inventoryTimestampExtractor = new InventoryTimestampExtractor();
        builder.stream("t-commodity-inventory",
                        Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null))
                //  null means → Kafka Streams will use the default reset policy from your streamsConfig.

                .to("t-commodity-inventory-four", Produced.with(stringSerde, inventorySerde));
    }
}

// reset policy = "where to start reading if no offset is found."
