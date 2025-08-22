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

    private static final Set<String> GOOD_WORDS = Set.of("happy","good","helpful");

    @Autowired
    void kStreamFeedBack(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedBackMessage.class);

        var goodFeedBackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde))
                .flatMapValues(mappersGoodWords());

        goodFeedBackStream.to("t-commodity-feedback-one-good");
    }

    private ValueMapper<FeedBackMessage, Iterable<String>> mappersGoodWords(){
        return feedback -> Arrays.asList(feedback.getFeedback().toLowerCase().split("\\s+")).stream()
                .filter(GOOD_WORDS::contains).distinct().collect(Collectors.toList());
    }
}
