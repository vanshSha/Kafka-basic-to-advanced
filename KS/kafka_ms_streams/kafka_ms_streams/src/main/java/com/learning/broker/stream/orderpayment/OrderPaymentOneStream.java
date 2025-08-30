package com.learning.broker.stream.orderpayment;

import com.learning.broker.message.OnlineOrderMessage;
import com.learning.broker.message.OnlineOrderPaymentMessage;
import com.learning.broker.message.OnlinePaymentMessage;
import com.learning.util.OnlineOrderTimestampExtractor;
import com.learning.util.OnlinePaymentTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.time.Duration;

//@Component
public class OrderPaymentOneStream {


    private OnlineOrderPaymentMessage joinOrderPayment(OnlineOrderMessage order, OnlinePaymentMessage payment) {
        var result = new OnlineOrderPaymentMessage();

        result.setOnlineOrderNumber(order.getOnlineOrderNumber());
        result.setOrderDateTime(order.getOrderDateTime());
        result.setTotalAmount(order.getTotalAmount());
        result.setUsername(order.getUsername());

        result.setPaymentDateTime(payment.getPaymentDateTime());
        result.setPaymentMethod(payment.getPaymentMethod());
        result.setPaymentNumber(payment.getPaymentNumber());

        return result;
    }

    @Autowired
    void kstreamOrderPayment(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OnlineOrderMessage.class);
        var paymentSerde = new JsonSerde<>(OnlinePaymentMessage.class);
        var orderPaymentSerde = new JsonSerde<>(OnlineOrderPaymentMessage.class);

        var orderStream = builder.stream("t-commodity-online-order", Consumed.with(stringSerde, orderSerde,
                new OnlineOrderTimestampExtractor(), null));

        var paymentStream = builder.stream("t-commodity-online-payment", Consumed.with(
                stringSerde, paymentSerde, new OnlinePaymentTimestampExtractor(), null));

        orderStream.join(
                        paymentStream,
                        (order, payment) -> this.joinOrderPayment(order, payment),
                        JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofHours(1l)),
                        // JoinWindows Kafka Streams needs to know how long it should “wait”, maximum waiting time for the partner event
                //   Keep the window open only for X time
                //   Duration.ofHours(1l)
                        StreamJoined.with(stringSerde, orderSerde, paymentSerde)
                        // StreamJoined -> Tell me how to read and write in database
                        // Which Serde to use for the key (orderId) ->   stringSerde - how ton seri/deseri the key (order_id)
                        // Which Serde to use for the order value
                        // Which Serde to use for the payment value
                        // join() -> How to  2 stream join
                        // StreamJoined = “Serde configuration for the
                        // temporary RocksDB stores Kafka uses during a join.”
                )
                .to("t-commodity-join-order-payment-one", Produced.with(stringSerde, orderPaymentSerde));


    }

}
