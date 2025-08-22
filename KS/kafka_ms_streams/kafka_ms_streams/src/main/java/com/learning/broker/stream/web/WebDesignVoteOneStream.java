package com.learning.broker.stream.web;

import com.learning.broker.message.WebColorVoteMessage;
import com.learning.broker.message.WebDesignVoteMessage;
import com.learning.broker.message.WebLayoutVoteMessage;
import com.learning.util.WebColorVoteTimestampExtractor;
import com.learning.util.WebLayoutVoteTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

//@Component
public class WebDesignVoteOneStream {

    private WebDesignVoteMessage voteJoiner(String color, String layout) {
        var result = new WebDesignVoteMessage();
        result.setColor(color);
        result.setLayout(layout);
        return result;
    }

    @Autowired
    void kstreamWebDesignVote(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var colorSerde = new JsonSerde<>(WebColorVoteMessage.class);
        var layoutSerde = new JsonSerde<>(WebLayoutVoteMessage.class);
        var designSerde = new JsonSerde<>(WebDesignVoteMessage.class);

        // Color table with String value
        var colorTable = builder.stream("t-commodity-web-vote-color",
                        Consumed.with(stringSerde, colorSerde,
                                new WebColorVoteTimestampExtractor(), null))
                .mapValues(
                        v -> v.getColor()
                )
                .toTable(Materialized.with(stringSerde, Serdes.String()));


        // Layout table with String value
        var layoutTable = builder.stream("t-commodity-web-vote-layout",
                        Consumed.with(stringSerde, layoutSerde,
                                new WebLayoutVoteTimestampExtractor(), null))
                .mapValues(
                        v -> v.getLayout()
                )
                .toTable();


        // Join to produce WebDesignVoteMessage
        var joinTable = colorTable.join(layoutTable, this::voteJoiner,
                Materialized.with(stringSerde, designSerde));

        joinTable.toStream().to(
                "t-commodity-web-vote-one-result",
                Produced.with(stringSerde, designSerde)
        );

        joinTable.groupBy(
                        (username, votedDesign) -> KeyValue.pair(votedDesign.getColor(), votedDesign.getColor())).count()
                .toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote one - color"));

        joinTable.groupBy(
                        (username, votedDesign) -> KeyValue.pair(votedDesign.getLayout(), votedDesign.getLayout())).count()
                .toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote one - layout"));


    }
}
