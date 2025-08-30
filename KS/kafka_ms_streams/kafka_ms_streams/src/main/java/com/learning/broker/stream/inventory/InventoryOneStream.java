package com.learning.broker.stream.inventory;

import com.learning.broker.message.InventoryMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

//@Component
public class InventoryOneStream {

    @Autowired
    void processInventoryStream(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var longSerde = Serdes.Long();

        builder.stream("t-commodity-inventory", Consumed.with(stringSerde, inventorySerde))
                .mapValues(
                        (item, inventory) -> inventory.getQuantity()
                ).groupByKey()
                // ()->0L means “for each new key, start counting from zero.”
                .aggregate( //   () -> 0L,
                        () -> 0L,
                        (aggKey, newValue, aggValue) -> aggValue + newValue,
                        // aggKey - item name
                        // newValue - the latest quantity for mapValues like 10
                        // aggValue → the running total (previous sum).
                        Materialized.with(stringSerde, longSerde)
                ).toStream().to("t-commodity-inventory-total-one",
                        Produced.with(stringSerde, longSerde));
    }
}

/*
docker exec -it kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic t-commodity-inventory-total-one \
  --from-beginning \
  --property print.key=true \
  --property print.value=true \
  --key-deserializer org.apache.kafka.common.serialization.StringDeserializer \
  --value-deserializer org.apache.kafka.common.serialization.LongDeserializer
 */