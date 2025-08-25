package com.learning.broker.stream.commodity;

import com.learning.broker.message.OrderMessage;
import com.learning.broker.message.OrderPatternMessage;
import com.learning.broker.message.OrderRewardMessage;
import com.learning.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;


//@Component
public class CommodityThreeStream {

    @Autowired
    void kStreamCommodityTrading(StreamsBuilder builder) {
        JsonSerde<OrderMessage> orderSerde = new JsonSerde<>(OrderMessage.class);
        JsonSerde<OrderPatternMessage> orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        JsonSerde<OrderRewardMessage> orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);
        Serde<String> stringSerde = Serdes.String();

        KStream<String, OrderMessage> maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(Serdes.String(), orderSerde))
                .mapValues(commodity -> CommodityStreamUtil.maskCreditCardNumber(commodity));


        maskedCreditCardStream.mapValues(commodity1 -> CommodityStreamUtil.convertToOrderPatternMessage(commodity1))
                .split()
                .branch(CommodityStreamUtil.isPlasticItem(),
                        Branched.<String, OrderPatternMessage>withConsumer(
                                ks -> ks.to("t-commodity-three-pattern-plastic",
                                        Produced.with(stringSerde, orderPatternSerde)))

                ).defaultBranch(
                        Branched.<String, OrderPatternMessage>withConsumer(
                                ks -> ks.to("t-commodity-three-pattern-nonplastic",
                                        Produced.with(stringSerde, orderPatternSerde))));


        maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheapItem())
                .map(CommodityStreamUtil.mapToOrderRewardChangeKey())
                .to("t-commodity-reward-three", Produced.with(Serdes.String(), orderRewardSerde));

        maskedCreditCardStream
                .selectKey(CommodityStreamUtil.generateStrongKey())
                .to("t-commodity-storage-three", Produced.with(Serdes.String(), orderSerde));
    }


}
