package com.learning.util;

import com.learning.broker.message.OnlinePaymentMessage;
import com.learning.broker.message.WebColorVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class WebColorVoteTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var message = (WebColorVoteMessage) record.value();
        return message != null ? message.getVoteDateTime() .toInstant().toEpochMilli()
                : record.timestamp();
    }
}
