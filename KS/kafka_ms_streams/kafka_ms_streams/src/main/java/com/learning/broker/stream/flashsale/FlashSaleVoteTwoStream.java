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
public class FlashSaleVoteTwoStream {

    // plusMinutes(2); -> add 2 min in current time
    //
    @Autowired
    void flashSaleVoteOneStream(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);
        var voteStart = OffsetDateTime.now().plusMinutes(2); // here sale start from current time + 2min
        var voteEnd = voteStart.plusHours(1); // here sale end current time + 2min to after 1 hour

        builder.stream("t-commodity-flashsale-vote", Consumed.with(stringSerde, flashSaleVoteSerde))
                .transformValues( // here i am creating new object same as flatMap
                        () -> new FlashSaleVoteTwoValueTransformer(voteStart, voteEnd))
                .filter( // if transformedValue is not null then fill filter otherwise dorp it.
                        (key, transformedValue) -> transformedValue != null)
                .map(                                          // here i am changing key - getCustomerId, value - getItemName
                        (key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()))
                .to("t-commodity-flashsale-vote-two-user-item");
        builder.table("t-commodity-flashsale-vote-two-user-item", Consumed.with(stringSerde, stringSerde))
                .groupBy( // here i am converting ktable into KGroupTable
                        (user, votedItem) -> KeyValue.pair(votedItem, votedItem))
                .count()
                .toStream()
                .to("t-commodity-flashsale-vote-two-result");

    }
}
