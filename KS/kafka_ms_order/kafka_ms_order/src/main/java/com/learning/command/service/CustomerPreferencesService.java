package com.learning.command.service;

import com.learning.api.request.CustomerPreferencesShoppingCartRequest;
import com.learning.api.request.CustomerPreferencesWishlistRequest;
import com.learning.command.action.CustomerPreferencesAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPreferencesService {


    @Autowired
    private CustomerPreferencesAction action;

    public void createShoppingCart(CustomerPreferencesShoppingCartRequest request) {
        action.publishShoppingCart(request);
    }

    public void createWishlist(CustomerPreferencesWishlistRequest request) {
        action.publishWishlist(request);
    }
}
