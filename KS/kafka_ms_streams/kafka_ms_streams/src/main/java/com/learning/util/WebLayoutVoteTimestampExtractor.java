package com.learning.util;

import com.learning.broker.message.WebColorVoteMessage;
import com.learning.broker.message.WebLayoutVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class WebLayoutVoteTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var message = (WebLayoutVoteMessage) record.value();
        return message != null ? message.getVoteDateTime() .toInstant().toEpochMilli()
                : record.timestamp();
    }
}
