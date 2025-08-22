package com.learning.command.action;

import com.learning.api.request.FeedBackRequest;
import com.learning.broker.message.FeedBackMessage;
import com.learning.broker.producer.FeedBackProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FeedBackAction {

    @Autowired
    private FeedBackProducer producer;

    public void publishToKafka(FeedBackRequest request) {
        var message = new FeedBackMessage();
        message.setFeedback(request.getFeedback());
        message.setLocation(request.getLocation());
        message.setRating(request.getRating());
        // random date time between last 7 days - now
        message.setFeedbackDateTime(OffsetDateTime.now().minusHours(ThreadLocalRandom.current().nextLong(7 * 7)));

        producer.publish(message);
    }

}
