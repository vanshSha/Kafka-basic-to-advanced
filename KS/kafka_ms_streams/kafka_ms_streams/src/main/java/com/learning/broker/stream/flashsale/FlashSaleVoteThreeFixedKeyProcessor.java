package com.learning.broker.stream.flashsale;

import com.learning.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.streams.processor.api.FixedKeyProcessor;
import org.apache.kafka.streams.processor.api.FixedKeyProcessorContext;
import org.apache.kafka.streams.processor.api.FixedKeyRecord;

import java.time.OffsetDateTime;

/*
FixedKeyProcessor -> in this interface key always same for all records in that operator
String -> Key
FlashSaleVoteMessage -> input data
FlashSaleVoteMessage -> output data
Processor API -> gives you full control over how records are processed, metadata, state stores,
 forwarding
 */
public class FlashSaleVoteThreeFixedKeyProcessor implements
        FixedKeyProcessor<String, FlashSaleVoteMessage, FlashSaleVoteMessage> {

    private final long voteStartTime;

    private final long voteEndTime;

    private FixedKeyProcessorContext processorContext; // only work with value because key is fixed

    public FlashSaleVoteThreeFixedKeyProcessor(OffsetDateTime startDateTime, OffsetDateTime endDateTime) {
        this.voteStartTime = startDateTime.toInstant().toEpochMilli();
        this.voteEndTime = endDateTime.toInstant().toEpochMilli();
    }

    @Override // this method is reading my metadata
    public void init(FixedKeyProcessorContext<String, FlashSaleVoteMessage> context) {
        this.processorContext = context;
    }

    /* process -> check if record is valid (based on time). Do something with each incoming record whenever new vote arrives
    then inside process i decide  -
    Keep it, drop it, modify it or forward it.
       FixedKeyRecord -> it means key is fixed and only the value change
     */
    @Override
    public void process(FixedKeyRecord<String, FlashSaleVoteMessage> record) {
        long recordTime = processorContext.currentStreamTimeMs();
        if (recordTime >= voteStartTime && recordTime <= voteEndTime) {
            processorContext.forward(record.withValue(record.value()));
        }
    }

    /*
    record.value() -> get the actual value
    record.withValue(record.value()) -> Creates a new FixedKeyRecord using the same key and timestamp
    forward() = “pass this record to the next step in my pipeline”.
    Without it, the pipeline breaks at that point.
*/

}