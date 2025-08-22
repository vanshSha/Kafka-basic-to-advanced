package com.learning.promotion;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;

//@Configuration
public class PromotionUpperCaseStream {

    @Bean
    KStream<String, String> kStreamPromotionUpperCase(StreamsBuilder builder) {
        KStream<String, String> sourceStream = builder.stream("t-commodity-promotion",
                Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, String> upperCaseStream = sourceStream.mapValues(promotion -> promotion.toUpperCase());
        upperCaseStream.to("t-commodity-promotion-uppercase", Produced.with(Serdes.String(), Serdes.String()));

        // useful for debugging, but it is better not to use this on production
        sourceStream.print(Printed.<String, String>toSysOut().withLabel("Original Stream"));
        upperCaseStream.print(Printed.<String, String>toSysOut().withLabel("Uppercase Stream"));

        return sourceStream;

    }

}
