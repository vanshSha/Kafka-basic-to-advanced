package com.learning.broker.stream.feedback;

import com.learning.broker.message.FeedBackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
public class FeedBackOneStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    @Autowired
    void kStreamFeedBack(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedBackMessage.class);
        // Produced.with(...) this is use for write  -> "How to write Java object

        // when i read key is String and Value is feedbackSerde
        var goodFeedBackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMapValues(mappersGoodWords());// work only with value output -> multiple values with  same key

        goodFeedBackStream.to("t-commodity-feedback-one-good");
        // Question why i am using mapvalue instead of flatMapValues
        // 1 input = 1 output. - map value
        // value -> 1 input = multiple output (like splitting sentences into words). flat
    }

    // this method is doing making them LowerCase -> filtering only good words, removing duplicate
    private ValueMapper<FeedBackMessage, Iterable<String>> mappersGoodWords() {
        return feedback -> Arrays.asList(feedback.getFeedback().toLowerCase().split("\\s+")).stream()
                .filter(words -> GOOD_WORDS.contains(words)).distinct().collect(Collectors.toList());
        // split("\\s+") =  break text into words, ignoring extra spaces.
        // words -> GOOD_WORDS.contains(words)  if words are present then return true otherwise false.
        // .distinct() this remove duplicate element from (according to this case ) Set.
        // collect it means streams end here and result gathered from container(like List, Set, Map, String, etc)

    }
}
