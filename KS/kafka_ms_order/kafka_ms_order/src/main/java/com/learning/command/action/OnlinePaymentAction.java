package com.learning.command.action;

import com.learning.api.request.OnlinePaymentRequest;
import com.learning.broker.message.OnlinePaymentMessage;
import com.learning.broker.producer.OnlinePaymentProducer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class OnlinePaymentAction {

    @Autowired
    private OnlinePaymentProducer producer;

    public void publishPaymentToKafka(OnlinePaymentRequest request) {
        var message = new OnlinePaymentMessage();

        message.setOnlineOrderNumber(request.getOnlineOrderNumber());
        message.setPaymentNumber("PAY-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase());
        message.setPaymentDateTime(
                request.getPaymentDateTime() == null ? OffsetDateTime.now() : request.getPaymentDateTime());
        message.setPaymentMethod(request.getPaymentMethod());

        producer.publish(message);
    }
}
