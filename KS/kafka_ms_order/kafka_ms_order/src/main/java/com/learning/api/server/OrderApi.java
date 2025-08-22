package com.learning.api.server;


import com.learning.api.request.OrderRequest;
import com.learning.api.response.OrderResponse;
import com.learning.command.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Autowired
    private OrderService service;



    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        // 1. save order using the service
            var orderNumber = service.saveOrder(request);

        // 2. construct OrderResponse
        var response = new OrderResponse(orderNumber);

        // 3. return the response
        return ResponseEntity.ok().body(response);
    }

}