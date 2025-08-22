package com.learning.command.service;

import com.learning.api.request.PremiumUserRequest;
import com.learning.command.action.PremiumUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremiumUserService {

    @Autowired
    private PremiumUserAction action;

    public void createUser(PremiumUserRequest request) {
        action.publishToKafka(request);
    }
}
