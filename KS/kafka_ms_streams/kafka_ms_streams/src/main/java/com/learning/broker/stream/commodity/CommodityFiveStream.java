package com.learning.broker.stream.commodity;

import com.learning.broker.message.OrderMessage;
import com.learning.broker.message.OrderPatternMessage;
import com.learning.broker.message.OrderRewardMessage;
import com.learning.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;


//@Component
public class CommodityFiveStream {

    private static final Logger LOG = LoggerFactory.getLogger(CommodityFiveStream.class);

    private void reportFraud(OrderMessage orderMessage) {
        LOG.info("Reporting fraud {}", orderMessage);
    }

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
                                ks -> ks.to("t-commodity-five-pattern-plastic",
                                        Produced.with(stringSerde, orderPatternSerde)))

                ).defaultBranch(
                        Branched.<String, OrderPatternMessage>withConsumer(
                                ks -> ks.to("t-commodity--five-pattern-nonplastic",
                                        Produced.with(stringSerde, orderPatternSerde))));


        maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheapItem())
                .map(CommodityStreamUtil.mapToOrderRewardChangeKey())
                .to("t-commodity-reward-five", Produced.with(Serdes.String(), orderRewardSerde));

        maskedCreditCardStream
                .selectKey(CommodityStreamUtil.generateStrongKey())
                .to("t-commodity-storage-five", Produced.with(Serdes.String(), orderSerde));

        maskedCreditCardStream.filter(
                        (k, v) -> v.getOrderLocation().toUpperCase().startsWith("C")).peek(
                        (k, v) -> reportFraud(v)).map(
                        (k, v) -> KeyValue.pair(v.getOrderLocation().toUpperCase().charAt(0) +
                                "***", (long) (v.getPrice() * v.getQuantity())))
                .to("t-commodity-fraud-five", Produced.with(stringSerde, Serdes.Long()));


    }


}

