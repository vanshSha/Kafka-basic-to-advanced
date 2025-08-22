//package Kafka_stream_sample.broker.stream;
//
//import Kafka_stream_sample.broker.message.PromotionMessage;
//import Kafka_stream_sample.broker.serde.PromotionSerde;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.Consumed;
//import org.apache.kafka.streams.kstream.Printed;
//import org.apache.kafka.streams.kstream.Produced;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
////@Component
//public class PromotionUpperCaseSpringJsonStream {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private static final Logger LOG = LoggerFactory.getLogger(PromotionUpperCaseSpringJsonStream.class);
//
//    @Autowired
//    void kstreamPromotionUpperCase(StreamsBuilder builder) {
//        var customSerde = new PromotionSerde();
//        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), customSerde));
//        var upperCaseStream = sourceStream.mapValues(this::upperCasePromotionCode);
//
//        upperCaseStream.to("t-commodity-promotion-uppercase", Produced.with(Serdes.String(), customSerde));
//
//        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Custom JSON Serde Original Stream"));
//        upperCaseStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Custom JSON Serde UpperCase Stream"));
//    }
//
//    private PromotionMessage upperCasePromotionCode(PromotionMessage promotionMessage){
//        return new PromotionMessage(promotionMessage.getPromotionCode().toUpperCase());
//    }
//}