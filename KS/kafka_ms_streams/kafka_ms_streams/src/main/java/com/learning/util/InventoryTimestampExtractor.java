package com.learning.util;

import com.learning.broker.message.InventoryMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

/*
TimestampExtractor -> decides the timestamp for each record
 */
public class InventoryTimestampExtractor implements TimestampExtractor {
    @Override
    //  extract -> What timestamp should I assign to this record for windowing, joins, aggregations, etc.
    /* ConsumerRecord<Object, Object> record =  object - key , object - value.
       long partitionTime ->
         - Kafka Streams always keeps track of → the largest timestamp it has seen so far in that partition.
       -That biggest timestamp is called the stream-time watermark (partitionTime).
       - have no timestamp (null),
       - or an invalid timestamp (like -1).

     */
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        var inventoryMessage = (InventoryMessage) record.value();
        return inventoryMessage != null ? inventoryMessage.getTransactionTime().toInstant().toEpochMilli()
                : record.timestamp();
        // inventoryMessage.getTransactionTime().toInstant().toEpochMilli() -> simple words get dateTime according to my
        // time zone then convert into UTC then covert into millisecond
    }
    // inventoryMessage.getTransactionTime().toInstant().toEpochMilli()
    //  toInstant -> a point on the UTC(Coordinate Universal Time) timeline. it gives Date,Time(in hours:minutes:seconds.millis), Z means Zulu times.
    // toEpochMilli() -> UTC convert into milliSecond
    // record.timestamp() -> byDefault Time Kafka  -> takes the timestamp from the producer. Sometimes producer decides (default).
   // Sometimes Kafka broker decides (if you configure LogAppendTime).

}
