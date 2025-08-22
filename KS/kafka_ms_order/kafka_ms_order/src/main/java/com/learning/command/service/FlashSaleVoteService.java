package com.learning.command.service;

import com.learning.api.request.FlashSaleVoteRequest;
import com.learning.command.action.FlashSaleVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleVoteService {

    @Autowired
    private FlashSaleVoteAction action;

    public void createFlashSaleVote(FlashSaleVoteRequest request){
        action.publishToKafka(request);
    }
}
