package Kafka_stream_sample.util;

import Kafka_stream_sample.broker.message.OrderMessage;

public class CommodityStreamUtil {

    public static OrderMessage maskCreditCardNumber(OrderMessage  orderMessage){
        String creditCardNumber = orderMessage.getCreditCardNumber();
        String maskedCreditCardNumber = "****-****-****-" + creditCardNumber.substring(creditCardNumber.length() - 4);

        return new OrderMessage(
                orderMessage.getOrderLocation(),
                orderMessage.getOrderNumber(),
                maskedCreditCardNumber,
                orderMessage.getOrderDateTime(),
                orderMessage.getItemName(),
                orderMessage.getPrice(),
                orderMessage.getQuantity());
    }
}
