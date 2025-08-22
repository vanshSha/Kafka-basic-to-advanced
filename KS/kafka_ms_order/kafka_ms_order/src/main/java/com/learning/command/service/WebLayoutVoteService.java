package com.learning.command.service;

import com.learning.api.request.WebLayoutVoteRequest;
import com.learning.command.action.WebLayoutVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebLayoutVoteService {

    @Autowired
    private WebLayoutVoteAction action;

    public void createLayoutVote(WebLayoutVoteRequest request) {
        action.publishToKafka(request);
    }

}
