package com.learning.util;

import com.learning.broker.message.OrderMessage;
import com.learning.broker.message.OrderPatternMessage;
import com.learning.broker.message.OrderRewardMessage;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;

import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Locale;

public class CommodityStreamUtil {
    public static OrderMessage maskCreditCardNumber(OrderMessage  orderMessage){
        String creditCardNumber = orderMessage.getCreditCardNumber(); // cardNumber + - = total 19
        String maskedCreditCardNumber = "****-****-****-" + creditCardNumber.substring(creditCardNumber.length() - 4);
                            //"HelloWorld"; -> str.substring(5) -> Hello
        return new OrderMessage(
                orderMessage.getOrderLocation(),
                orderMessage.getOrderNumber(),
                maskedCreditCardNumber,
                orderMessage.getOrderDateTime(),
                orderMessage.getItemName(),
                orderMessage.getPrice(),
                orderMessage.getQuantity());
    }

    public static OrderPatternMessage convertToOrderPatternMessage(OrderMessage orderMessage){
        String itemName = orderMessage.getItemName();
        long totalAmount = orderMessage.getPrice() * orderMessage.getQuantity();
        OffsetDateTime orderDateTime = orderMessage.getOrderDateTime();
        String orderLocation = orderMessage.getOrderLocation();
        String orderNumber = orderMessage.getOrderNumber();

        return new OrderPatternMessage(
                itemName,
                totalAmount,
                orderDateTime,
                orderLocation,
                orderNumber);
    }

    public static OrderRewardMessage convertToOrderRewardMessage(OrderMessage orderMessage){
        String orderLocation = orderMessage.getOrderLocation();
        String orderNumber = orderMessage.getOrderNumber();

        OffsetDateTime orderDateTime = orderMessage.getOrderDateTime();
        String itemName = orderMessage.getItemName();
        int price = orderMessage.getPrice();
        int quantity = orderMessage.getQuantity();
        return new OrderRewardMessage(
                orderLocation,
                orderNumber,
                orderDateTime,
                itemName,
                price,
                quantity);
    }


    public static Predicate<String, OrderMessage> isLargeQuantity() {
        return (key, orderMessage) -> orderMessage.getQuantity() > 200;
    }

    public static Predicate<String, OrderPatternMessage> isPlasticItem(){
        return (key, msg) -> {
            if(msg == null) return false;
            String name = msg.getItemName();
            if(name == null) return false;
            return name.toUpperCase(Locale.ROOT).startsWith("PLASTIC");
        };
    }

    public static Predicate<String, OrderMessage> isCheapItem(){
        return (key, orderMessage) ->orderMessage.getPrice() < 100;
    }

    // functional interface (k,V, KR) . it is used to a key , Value pair into a new kR
    // I want produce
    public static KeyValueMapper<String, OrderMessage, String> generateStrongKey(){
        return(key, orderMessage) -> Base64.getEncoder().encodeToString(orderMessage.getOrderNumber().getBytes());
    }                                                 // Base64.getEncoder().encodeToString -> encoded byte into Base64 String ex - "FHEJEJdfne-=="
      // why choose -> don't contain special char. convert ORD-123 into special char "I1NwZWNpYWxLZXk"


    public static KeyValueMapper<String, OrderMessage, KeyValue<String, OrderRewardMessage>> mapToOrderRewardChangeKey(){
        return (key, orderMessage) -> KeyValue.pair(orderMessage.getOrderLocation(),
                convertToOrderRewardMessage(orderMessage));
    }
}

