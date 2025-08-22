package com.learning.util;

import com.learning.broker.message.OnlinePaymentMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class OnlinePaymentTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var onlinePaymentMessage = (OnlinePaymentMessage) record.value();
        return onlinePaymentMessage != null ? onlinePaymentMessage.getPaymentDateTime().toInstant().toEpochMilli()
                : record.timestamp();
    }
}
