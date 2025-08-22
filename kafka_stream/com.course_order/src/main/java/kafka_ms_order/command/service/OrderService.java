package kafka_ms_order.command.service;

import kafka_ms_order.api.request.OrderRequest;
import kafka_ms_order.broker.message.OrderMessage;
import kafka_ms_order.command.action.OrderAction;
import kafka_ms_order.entity.Order;
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
