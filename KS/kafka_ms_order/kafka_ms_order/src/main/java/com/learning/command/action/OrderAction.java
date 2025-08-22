package com.learning.command.action;

import com.learning.api.request.OrderRequest;
import com.learning.broker.message.OrderMessage;
import com.learning.broker.producer.OrderProducer;
import com.learning.entity.Order;
import com.learning.entity.OrderItem;
import com.learning.repository.OrderItemRepository;
import com.learning.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class OrderAction {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    public Order convertToOrder(OrderRequest request) {
        var order = new Order();
        order.setOrderLocation(request.getOrderLocation());
        order.setCreditCardNumber(request.getCreditCardNumber());
        order.setOrderDateTime(OffsetDateTime.now());
        order.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        var orderItems = request.getItems().stream().map(
                item -> {
                    var orderItem = new OrderItem();
                    orderItem.setItemName(item.getIteamName());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                }).toList();
                order.setOrderItems(orderItems);
        return order;
    }


    public void saveToDatabase(Order orderEntity) {
        orderRepository.save(orderEntity);
        orderEntity.getOrderItems().forEach(orderItemRepository::save);
    }

    public OrderMessage convertToOrderMessage(OrderItem orderItem) {
        var orderMessage = new OrderMessage();
        orderMessage.setItemName(orderItem.getItemName());
        orderMessage.setPrice(orderItem.getPrice());
        orderMessage.setQuantity(orderItem.getQuantity());
        orderMessage.setOrderNumber(orderItem.getOrder().getOrderNumber());
        orderMessage.setOrderDateTime(orderItem.getOrder().getOrderDateTime());
        orderMessage.setCreditCardNumber(orderItem.getOrder().getCreditCardNumber());
        orderMessage.setOrderLocation(orderItem.getOrder().getOrderLocation());
 
        return orderMessage;
    }

    public void sendToKafka(OrderMessage orderMessage) {
        orderProducer.sendOrder(orderMessage);
    }
}
