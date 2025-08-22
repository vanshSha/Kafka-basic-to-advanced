package com.learning.command.service;

import com.learning.api.request.FeedBackRequest;
import com.learning.command.action.FeedBackAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedBackService {

    @Autowired
    private FeedBackAction action;

    public void createFeedback(FeedBackRequest request) {
        action.publishToKafka(request);
    }

}
