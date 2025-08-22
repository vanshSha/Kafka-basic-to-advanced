package Kafka_stream_sample.broker.stream;

    import Kafka_stream_sample.broker.message.PromotionMessage;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.apache.kafka.common.serialization.Serdes;
    import org.apache.kafka.streams.StreamsBuilder;
    import org.apache.kafka.streams.kstream.Consumed;
    import org.apache.kafka.streams.kstream.Printed;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    //@Component
    public class PromotionJsonUpperStream {

        @Autowired
        private ObjectMapper objectMapper;

        private static final Logger LOG = LoggerFactory.getLogger(PromotionJsonUpperStream.class);

        @Autowired
        void kstreamPromotionUpperCase(StreamsBuilder builder){
            var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), Serdes.String()));
            var upperCaseStream = sourceStream.mapValues(this::upperCasePromotionCode);

            upperCaseStream.to("t-commodity-promotion-uppercase");
            sourceStream.print(Printed.<String,String>toSysOut().withLabel("JSON Original Stream"));
            upperCaseStream.print(Printed.<String,String>toSysOut().withLabel("JSON UpperCase Stream"));
        }

        private String upperCasePromotionCode(String jsonString){
            try{
                var promotion = objectMapper.readValue(jsonString, PromotionMessage.class);
                promotion.setPromotionCode(promotion.getPromotionCode().toUpperCase());
                return objectMapper.writeValueAsString(promotion);
            }catch(Exception e){
                LOG.warn("Unable to process JSON ", e);
                return "";
            }
        }
    }