package com.learning.broker.stream.promotion;

import com.learning.broker.message.PromotionMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class PromotionUpperCaseSpringJsonStream {

    @Autowired
    void kStreamPromotionUpperCase(StreamsBuilder builder) {
       // var customSerde = new PromotionSerde();
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), new JsonSerde<>(PromotionMessage.class)));
        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Custom JSON Serde Original Stream"));

        var upperCaseStream = sourceStream.mapValues(this::upperCasePromotionCode);
        upperCaseStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Custom JSON Serde UpperCase Stream"));


        upperCaseStream.to("t-commodity-promotion-uppercase", Produced.with(Serdes.String(), new JsonSerde<>(PromotionMessage.class)));


    }

    private PromotionMessage upperCasePromotionCode(PromotionMessage promotionMessage){
        return new PromotionMessage(promotionMessage.getPromotionCode().toUpperCase());
    }
}