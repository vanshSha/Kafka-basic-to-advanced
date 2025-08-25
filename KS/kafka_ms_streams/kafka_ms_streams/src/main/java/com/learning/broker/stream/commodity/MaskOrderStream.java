package com.learning.broker.stream.commodity;

import com.learning.broker.message.OrderMessage;
import com.learning.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

//@Component
public class MaskOrderStream  { // mapValues only effect value instead of key

    @Autowired
    void kStreamCommodityMask(StreamsBuilder builder){
        var orderSerde = new JsonSerde<>(OrderMessage.class);// creating kStream for topic t-commodity-order
        KStream maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(Serdes.String(), orderSerde))
                .mapValues(order -> CommodityStreamUtil.maskCreditCardNumber(order));
        maskedCreditCardStream.to("t-commodity-order-masked", Produced.with(Serdes.String(), orderSerde));
        maskedCreditCardStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked Order Stream"));
    }
}
