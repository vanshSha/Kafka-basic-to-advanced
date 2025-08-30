package com.learning.broker.stream.premium;

import com.learning.broker.message.PremiumOfferMessage;
import com.learning.broker.message.PremiumPurchaseMessage;
import com.learning.broker.message.PremiumUserMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.List;
// innerjoin stream
// key of stream and table are same then write data otherwise doesn't do process
//@Component
public class PremiumOfferOneStream {

    private PremiumOfferMessage joiner(PremiumPurchaseMessage purchase, PremiumUserMessage user){
        var result = new PremiumOfferMessage();

        result.setUsername(purchase.getUsername());
        result.setPurchaseNumber(purchase.getPurchaseNumber());
        result.setLevel(user.getLevel());

        return result;
    }

    @Autowired
    void kstreamPremiumOffer(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var purchaseSerde = new JsonSerde<>(PremiumPurchaseMessage.class);
        var userSerde = new JsonSerde<>(PremiumUserMessage.class);
        var offerSerde = new JsonSerde<>(PremiumOfferMessage.class);

        var purchaseStream = builder.stream("t-commodity-premium-purchase",
                Consumed.with(stringSerde, purchaseSerde)).selectKey(
                (k, v) -> v.getUsername());

        var filterLevel = List.of("gold", "diamond", "silver");
        var userTable = builder.table("t-commodity-premium-user",
                Consumed.with(stringSerde, userSerde)).filter(
                (k, v) -> filterLevel.contains(v.getLevel().toLowerCase()));

        purchaseStream.join(userTable, this::joiner, Joined.with(stringSerde, purchaseSerde, userSerde))
                .peek((k, v) -> System.out.println("Joined Result: " + k + " -> " + v))
                .to("t-commodity-premium-offer-one", Produced.with(stringSerde, offerSerde));

    }

}

/*
 KS % docker exec -it kafka kafka-console-consumer \
--bootstrap-server localhost:9092 \
--topic t-commodity-premium-offer-one \
--from-beginning \
--property print.key=true \    -> shows the Kafka key.
--property print.value=true    -> shows the message content.

 */
