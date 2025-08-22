package com.learning.command.action;

import com.learning.api.request.WebLayoutVoteRequest;
import com.learning.broker.message.WebLayoutVoteMessage;
import com.learning.broker.producer.WebLayoutVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebLayoutVoteAction {

    @Autowired
    private WebLayoutVoteProducer producer;

    public void publishToKafka(WebLayoutVoteRequest request) {
        var message = new WebLayoutVoteMessage();

        message.setUsername(request.getUsername());
        message.setLayout(request.getLayout());
        message.setVoteDateTime(request.getVoteDateTime());

        producer.publish(message);
    }
}
