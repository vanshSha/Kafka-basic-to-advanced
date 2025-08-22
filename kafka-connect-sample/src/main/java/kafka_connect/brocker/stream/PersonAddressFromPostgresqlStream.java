package kafka_connect.brocker.stream;

import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
public class PersonAddressFromPostgresqlStream {

    @Autowired
    void kstreamPersonAddressFromPostgresql(StreamsBuilder builder){
        var sourceStream = builder.stream("t-person-address-postgresql");
        sourceStream.to("to-person-address-target");

    }
}
