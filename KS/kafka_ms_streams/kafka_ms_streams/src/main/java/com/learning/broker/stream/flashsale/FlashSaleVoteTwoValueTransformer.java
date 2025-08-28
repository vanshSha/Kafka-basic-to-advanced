package com.learning.broker.stream.flashsale;

import com.learning.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.OffsetDateTime;

/*
ValueTransformer : This is interface in kafka Stream for custom value transformation.

 */
public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

    private final long voteStartTime;
    private final long voteEndTime;
    private ProcessorContext processorContext;

    public FlashSaleVoteTwoValueTransformer(OffsetDateTime startDataTime, OffsetDateTime endDateTime) {
        this.voteStartTime = startDataTime.toInstant().toEpochMilli();
        this.voteEndTime = endDateTime.toInstant().toEpochMilli();
    }

    @Override
    public void init(ProcessorContext context) {
        this.processorContext = context;
    }

    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
        var recordTime = processorContext.timestamp();
        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
    }

    @Override
    public void close() {

    }
}
