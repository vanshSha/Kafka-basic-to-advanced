package com.learning.command.service;

import com.learning.api.request.WebColorVoteRequest;
import com.learning.command.action.WebColorVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebColorVoteService {

    @Autowired
    private WebColorVoteAction action;

    public void createColorVote(WebColorVoteRequest request){
        action.publishToKafka(request);
    }
}
