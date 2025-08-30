package com.learning.broker.stream.inventory;

import com.learning.broker.message.InventoryMessage;
import com.learning.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

// hopping time window
// same problem like
//@Component
public class InventorySevenStream {

    @Autowired
    void kstreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var inventoryTimestampExtractor = new InventoryTimestampExtractor();
        var longSerde = Serdes.Long();
        var inactivityGap = Duration.ofMinutes(30);
        var windowSerde = WindowedSerdes.sessionWindowedSerdeFrom(String.class);

        builder.stream("t-commodity-inventory",
                        Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null))
                .groupByKey()
                .windowedBy(SessionWindows.ofInactivityGapAndGrace(inactivityGap, Duration.ofMinutes(5)))
                .count()
                .toStream()
                .filter(
                        (k, v) -> v != null && v > 0
                ) // filter out empty sessions
                .peek(
                        (k, v) -> {
                            var windowStartTime = Instant.ofEpochMilli(k.window().start()).atOffset(ZoneOffset.UTC);
                            var windowEndTime = Instant.ofEpochMilli(k.window().end()).atOffset(ZoneOffset.UTC);

                            System.out.println("[" + k.key() + "@" + windowStartTime + "/" + windowEndTime + "], " + v);
                        })
                .to("t-commodity-inventory-seven", Produced.with(windowSerde, longSerde));
    }

}