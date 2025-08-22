package com.learning.broker.stream.flashsale;

import com.learning.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

//@Component
public class FlashSaleVoteThreeStream {

    @SuppressWarnings("deprecation")
    @Autowired
    void flashSaleVoteOneStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);
//        var voteStart = OffsetDateTime.now().plusMinutes(2);
//        var voteEnd = voteStart.plusHours(1);
        var voteStart = OffsetDateTime.parse("2020-01-01T00:00:00Z");
        var voteEnd = OffsetDateTime.parse("2030-01-01T00:00:00Z");


        builder.stream("t-commodity-flashsale-vote", Consumed.with(stringSerde, flashSaleVoteSerde))
                .processValues(
                        () -> new FlashSaleVoteThreeFixedKeyProcessor(voteStart, voteEnd))
                .filter(
                        (key,transformedValue) -> transformedValue != null)
                .map(
                        (key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()))
                .to("t-commodity-flashsale-vote-three-user-item");
        builder.table("t-commodity-flashsale-vote-three-user-item", Consumed.with(stringSerde, stringSerde))
                .groupBy(
                        (user, votedItem) -> KeyValue.pair(votedItem, votedItem))
                .count()
                .toStream()
                .to("t-commodity-flashsale-vote-three-result");

    }
}
