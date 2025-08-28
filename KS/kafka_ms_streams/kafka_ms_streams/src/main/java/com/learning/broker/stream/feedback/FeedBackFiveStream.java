package com.learning.broker.stream.feedback;

import com.learning.broker.message.FeedBackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
public class FeedBackFiveStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

/*
SuppressWarnings - Warnings are shown at compile time, not at runtime.
"deprecation" - this method still works, but it is old and may remove in the  future
"unchecked" - Unchecked warning means the compiler cannot ensure type safety
 ex -  Example: assigning raw List to List<String> → compiler says: “I can’t check this safely.” */
    @SuppressWarnings({"deprecation", "unchecked"})
    @Autowired
    void kStreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedBackMessage.class);

         builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMap(splitWords())
                .split()
                .branch(isGoodWord(), Branched.withConsumer(
                        ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-good"))
                                .groupByKey().count().toStream().to("t-commodity-feedback-five-good-count")))
                .branch(isBadWord(),
                        Branched.withConsumer(
                                ks -> ks.repartition(Repartitioned.as("t-commodity-feedback-five-bad"))
                                        .groupByKey().count().toStream().to("t-commodity-feedback-five-bad-count")
                        )
                        );
//                .branch(isGoodWord(), isBadWord());
//        feedbackStreams[0].through("t-commodity-feedback-five-good")
//                .groupByKey().count().toStream().to("t-commodity-feedback-five-good-count");
//
//        feedbackStreams[1].through("t-commodity-feedback-five-bad")
//                .groupByKey().count().toStream().to("t-commodity-feedback-five-bad-count");
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