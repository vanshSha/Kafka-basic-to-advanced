package com.learning.broker.stream.promotion;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

//@Configuration
public class PromotionUpperCaseStr {

    //@Bean
    KStream<String, String> kStreamPromotionUpperCaseV2(StreamsBuilder builder) {
        KStream<String, String> sourceStream = builder.stream("t-commodity-promotion",
                Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, String> uppercaseStream = sourceStream.mapValues(promotion -> promotion.toUpperCase());
        uppercaseStream.to("t-commodity-promotion-uppercase", Produced.with(Serdes.String(), Serdes.String()));

        // useful for debugging it is better not to use for production
        sourceStream.print(Printed.<String, String>toSysOut().withLabel("Original Stream"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("Uppercase Stream"));

        return sourceStream;

    }
}
