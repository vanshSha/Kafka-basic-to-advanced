package com.learning.command.action;

import com.learning.api.request.CustomerPreferencesShoppingCartRequest;
import com.learning.api.request.CustomerPreferencesWishlistRequest;
import com.learning.broker.message.CustomerPreferenceShoppingCartMessage;
import com.learning.broker.message.CustomerPreferenceWishlistMessage;
import com.learning.broker.producer.CustomerPreferencesProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class CustomerPreferencesAction {

    @Autowired
    private CustomerPreferencesProducer producer;

    public void publishShoppingCart(CustomerPreferencesShoppingCartRequest request) {
        var message = new CustomerPreferenceShoppingCartMessage(request.getCustomerId(), request.getItemName(),
                request.getCartAmount(), OffsetDateTime.now());

        producer.publishShoppingCart(message);
    }

    public void publishWishlist(CustomerPreferencesWishlistRequest request) {
        var message = new CustomerPreferenceWishlistMessage(request.getCustomerId(), request.getItemName(),
                OffsetDateTime.now());

        producer.publishWishlist(message);
    }
}
