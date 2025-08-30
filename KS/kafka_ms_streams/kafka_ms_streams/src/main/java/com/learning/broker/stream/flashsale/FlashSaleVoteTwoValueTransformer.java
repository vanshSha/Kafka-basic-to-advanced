package com.learning.broker.stream.flashsale;

import com.learning.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.OffsetDateTime;

/*
Processor (high-level) API.
ValueTransformer : This is interface in kafka Stream for custom value transformation.(stateful if needed)

 */                                              // V - means input value type  VR - output Value type
public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {
                                                                            // V                  VR
    private final long voteStartTime;
    private final long voteEndTime;
    private ProcessorContext processorContext;// interface use for get information about metadata(information about record) and stateStore(small database inside kafka stream)

    public FlashSaleVoteTwoValueTransformer(OffsetDateTime startDateTime, OffsetDateTime endDateTime) {
        this.voteStartTime = startDateTime.toInstant().toEpochMilli();
        this.voteEndTime = endDateTime.toInstant().toEpochMilli();
    }

    @Override // with this method i can read metadata
    public void init(ProcessorContext context) {
        this.processorContext = context;
    }

    // timestamp(); -> give the event time of record (not system) used to make time-based decisions.
    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
        long recordTime = processorContext.timestamp();
        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
    }

    @Override
    public void close() {

    }
}
