package com.learning.command.action;

import com.learning.api.request.FlashSaleVoteRequest;
import com.learning.broker.message.FlashSaleVoteMessage;
import com.learning.broker.producer.FlashSaleVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlashSaleVoteAction {

@Autowired
 private FlashSaleVoteProducer producer;

public void publishToKafka(FlashSaleVoteRequest request){
    var message = new FlashSaleVoteMessage();

    message.setCustomerId(request.getCustomerId());
    message.setItemName(request.getItemName());
    producer.publish(message);
}

}
