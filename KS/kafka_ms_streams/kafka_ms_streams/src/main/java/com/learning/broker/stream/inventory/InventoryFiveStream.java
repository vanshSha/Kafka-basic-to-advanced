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
// here it the issue i didn't understand where i did make mistake. The problem is mismatch serde. it isn't running
// tumbling time window
//@Component
public class InventoryFiveStream {

    @Autowired
    void kstreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var inventoryTimestampExtractor = new InventoryTimestampExtractor();
        var longSerde = Serdes.Long();
        var windowLength = Duration.ofHours(1l);
        var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());
        // here i am creating serde for timeWindowSerde

        builder.stream("t-commodity-inventory",
                        Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null))
                .mapValues(
                        (k, v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity())
                .groupByKey()
                // windowedBy - time based grouping
                // TimeWindows - define fixed size window
                // ofSizeWithNoGrace - create a fixed size window
                .windowedBy(TimeWindows.ofSizeWithNoGrace(windowLength))

/*       .reduce -> combines values by key. kafka Stream Keeps just the final combined value
 */
                //(agg , newValue) -> Long.sum(agg , newValue)
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde))
                .toStream()
                .peek(
                        (k, v) -> {
                            var windowStartTime = Instant.ofEpochMilli(k.window().start()).atOffset(ZoneOffset.UTC);
                            var windowEndTime = Instant.ofEpochMilli(k.window().end()).atOffset(ZoneOffset.UTC);

                            System.out.println("[" + k.key() + "@" + windowStartTime + "/" + windowEndTime + "], " + v);
                        })
                .to("t-commodity-inventory-five", Produced.with(windowSerde, longSerde));
    }

}