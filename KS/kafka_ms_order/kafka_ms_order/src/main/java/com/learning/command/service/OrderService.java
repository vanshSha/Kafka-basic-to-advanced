package com.learning.command.service;


import com.learning.api.request.OrderRequest;
import com.learning.broker.message.OrderMessage;
import com.learning.command.action.OrderAction;
import com.learning.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderAction action;

    public String saveOrder(OrderRequest request) {
        Order orderEntity = action.convertToOrder(request);

        action.saveToDatabase(orderEntity);

        orderEntity.getOrderItems().forEach(item -> {
            OrderMessage orderMessage = action.convertToOrderMessage(item);

            action.sendToKafka(orderMessage);
        });

        return orderEntity.getOrderNumber();
    }
}
