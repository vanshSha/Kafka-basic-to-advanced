package com.learning.command.service;

import com.learning.api.request.CustomerPurchaseMobileRequest;
import com.learning.api.request.CustomerPurchaseWebRequest;
import com.learning.command.action.CustomerPurchaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPurchaseService {

    @Autowired
    private CustomerPurchaseAction action;

    public String createdPurchaseMobile(CustomerPurchaseMobileRequest request) {
        return action.publishMobileToKafka(request);
    }

    public String createPurchaseWeb(CustomerPurchaseWebRequest request) {
        return action.publishWebToKafka(request);
    }
}
