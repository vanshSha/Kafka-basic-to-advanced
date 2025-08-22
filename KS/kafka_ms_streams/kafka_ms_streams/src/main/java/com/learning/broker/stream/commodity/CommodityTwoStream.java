package com.learning.broker.stream.commodity;

import com.learning.broker.message.OrderMessage;
import com.learning.broker.message.OrderPatternMessage;
import com.learning.broker.message.OrderRewardMessage;
import com.learning.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;


//@Component
public class CommodityTwoStream {

    @Autowired
    void kstreamCommodityTrading(StreamsBuilder builder) {
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);
        var stringSerde = Serdes.String();

        var maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(Serdes.String(), orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCardNumber);

        maskedCreditCardStream.mapValues(CommodityStreamUtil::convertToOrderPatternMessage)
                .split()
                .branch(CommodityStreamUtil.isPlasticItem(),
                        Branched.<String, OrderPatternMessage>withConsumer(
                                ks -> ks.to("t-commodity-two-pattern-plastic",
                                        Produced.with(stringSerde, orderPatternSerde)))

                ).defaultBranch(
                        Branched.<String, OrderPatternMessage>withConsumer(
                                ks -> ks.to("t-commodity-two-pattern-nonplastic",
                                        Produced.with(stringSerde, orderPatternSerde))));


        maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheapItem())
                .mapValues(CommodityStreamUtil::convertToOrderRewardMessage)
                .to("t-commodity-reward-two", Produced.with(Serdes.String(), orderRewardSerde));

        maskedCreditCardStream
                .selectKey(CommodityStreamUtil.generateStrongKey())
                .to("t-commodity-storage-two", Produced.with(Serdes.String(), orderSerde));
    }


}
