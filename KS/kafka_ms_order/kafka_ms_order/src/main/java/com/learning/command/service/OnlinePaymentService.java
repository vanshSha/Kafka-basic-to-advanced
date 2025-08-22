package com.learning.command.service;

import com.learning.api.request.OnlinePaymentRequest;
import com.learning.command.action.OnlinePaymentAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentService {

    @Autowired
    private OnlinePaymentAction action;

    public void pay(OnlinePaymentRequest request){
        action.publishPaymentToKafka(request);
    }
}
