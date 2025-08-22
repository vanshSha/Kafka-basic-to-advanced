package com.learning.util;

import com.learning.broker.message.OnlineOrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class OnlineOrderTimestampExtractor implements TimestampExtractor {


    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var onlineOrderMessage = (OnlineOrderMessage) record.value();
        return onlineOrderMessage != null ? onlineOrderMessage.getOrderDateTime().toInstant().toEpochMilli()
                : record.timestamp();
    }
}
