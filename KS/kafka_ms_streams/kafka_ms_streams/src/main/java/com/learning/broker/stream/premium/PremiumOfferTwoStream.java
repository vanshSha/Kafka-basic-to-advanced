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

// left join . i am joining kStream and kTable
//@Component
public class PremiumOfferTwoStream {
    //PremiumPurchaseMessage.,PremiumUserMessage
    private PremiumOfferMessage joiner(PremiumPurchaseMessage purchase, PremiumUserMessage user) {
        var result = new PremiumOfferMessage();

        result.setUsername(purchase.getUsername());
        result.setPurchaseNumber(purchase.getPurchaseNumber());

        if (user != null) {
            result.setLevel(user.getLevel());
        }

        return result;
    }

    @Autowired
    void kstreamPremiumOffer(StreamsBuilder builder) {
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

        purchaseStream.leftJoin(userTable, (purchase, user) -> this.joiner(purchase, user), Joined.with(stringSerde, purchaseSerde, userSerde))
                .peek((k, v) -> System.out.println("Joined Result: " + k + " -> " + v))
                .filter(
                        (k, v) -> v.getLevel() != null
                )
                .to("t-commodity-premium-offer-two", Produced.with(stringSerde, offerSerde));

    }

}
