package com.learning.api.server;

import com.learning.api.request.CustomerPreferencesShoppingCartRequest;
import com.learning.api.request.CustomerPreferencesWishlistRequest;
import com.learning.command.service.CustomerPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/preference")
public class CustomerPreferencesApi {

    @Autowired
    private CustomerPreferencesService service;

    @PostMapping(value = "/shopping-cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createShoppingCart(@RequestBody CustomerPreferencesShoppingCartRequest request) {
        service.createShoppingCart(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Added shopping cart " + request.getItemName() + " for customer " + request.getCustomerId());
    }

    @PostMapping(value = "/wishlist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createWishlist(@RequestBody CustomerPreferencesWishlistRequest request) {
        service.createWishlist(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Added wishlist " + request.getItemName() + " for customer " + request.getCustomerId());
    }

}
