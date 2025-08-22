package com.learning.broker.stream.feedback;

import com.learning.broker.message.FeedBackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
public class FeedBackThreeStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    @Autowired
    void kstreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedBackMessage.class);

        builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMap(splitWords())
                .split()
                .branch(isGoodWord(), Branched.withConsumer(
                ks -> ks.to("t-commodity-feedback-three-good")
                )).branch(isBadWord(), Branched.withConsumer(
                        ks -> ks.to("t-commodity-feedback-three-bad")
                ));
    }

    private Predicate<String, String> isBadWord() {
        return (kay, value) -> BAD_WORDS.contains(value);
    }

    private Predicate<String, String> isGoodWord() {
        return (kay, value) -> GOOD_WORDS.contains(value);
    }

    private KeyValueMapper<String, FeedBackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (key, value) -> Arrays.stream(value.getFeedback()
                .replaceAll("[^a-zA-Z ]", "")
                .split("\\s+"))

                .distinct()
                .map(word -> KeyValue.pair(value.getLocation(), word))
                .collect(Collectors.toList());
    }
}