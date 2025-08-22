package com.learning.broker.producer;

import com.learning.broker.message.CustomerPreferenceShoppingCartMessage;
import com.learning.broker.message.CustomerPreferencesWishlistMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerPreferencesProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishShoppingCart(CustomerPreferenceShoppingCartMessage message) {
        kafkaTemplate.send("t-commodity-customer-preference-shopping-cart", message.getCustomerId(), message);
    }

    public void publishWishlist(CustomerPreferencesWishlistMessage message) {
        kafkaTemplate.send("t-commodity-customer-preference-wishlist", message.getCustomerId(), message);
    }

}
