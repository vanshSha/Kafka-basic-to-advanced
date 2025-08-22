package com.learning.command.service;

import com.learning.api.request.OnlineOrderRequest;
import com.learning.command.action.OnlineOrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineOrderService {

    @Autowired
    private OnlineOrderAction action;

    public void saveOnlineOrder(OnlineOrderRequest request) {
        action.publishToKafka(request);
    }
}
