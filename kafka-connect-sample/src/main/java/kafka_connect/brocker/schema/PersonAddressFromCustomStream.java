package kafka_connect.brocker.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka_connect.brocker.message.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//@Component
public class PersonAddressFromCustomStream {

    private static final Logger LOG = LoggerFactory.getLogger(PersonAddressFromCustomStream.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    void kstreamPersonAddressFromCustom(StreamsBuilder builder) {

        var sourceStream = builder.stream("t-person-address-custom", Consumed.with(Serdes.String(), Serdes.String()));
        var targetStream = sourceStream.flatMap(expandPersonAddress());
        targetStream.to("t-person-address-target", Produced.with(Serdes.String(), Serdes.String()));

    }

    private KeyValueMapper<String, String, Iterable<KeyValue<String, String>>> expandPersonAddress() {
        return (key, value) -> {
            var expanded = new ArrayList<KeyValue<String, String>>();

            try {
                var original = objectMapper.readValue(value, PersonMessage.class);

                for (var address : original.getAddresses()) {
                    var targetKey = generateTargetKey(address.getAddressId());
                    var targetValue = generateTargetValue(original, address);
                    expanded.add(KeyValue.pair(targetKey, targetValue));

                }
            } catch (Exception e) {
                LOG.error("Invalid data : {} throws : {}", value, e.getMessage());
            }

            return expanded;
        };
    }

    private String generateTargetKey(int addressId) throws JsonProcessingException {
        var targetKey = new KafkaConnectMessage<Integer>();
        targetKey.setSchema(KafkaConnectPersonAddressTargetKeySchema.instance());
        targetKey.setPayLoad(addressId);

        return objectMapper.writeValueAsString(targetKey);
    }

    private String generateTargetValue(PersonMessage person, AddressMessage address)
            throws JsonProcessingException {
        var targetPayload = new KafkaConnectPersonTargetMessage();

        targetPayload.setEmail(person.getEmail());
        targetPayload.setPersonId(person.getPersonId());
        targetPayload.setFullName(person.getFullName());
        targetPayload.setAddress(address.getAddress());
        targetPayload.setCity(address.getCity());
        targetPayload.setPostalCode(address.getPostalCode());
        targetPayload.setAddressId(address.getAddressId());

        var targetValue = new KafkaConnectMessage<KafkaConnectPersonTargetMessage>();
        targetValue.setSchema(KafkaConnectPersonAddressTargetValueSchema.instance());
        targetValue.setPayLoad(targetPayload);

        return objectMapper.writeValueAsString(targetValue);
    }

}
