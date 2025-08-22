package kafka_connect.brocker.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaConnectSchema {

    public enum Type {
        struct, string, int32
    }

    private String type;
    private boolean optional;
    private String field;
    private List<KafkaConnectSchema> fields;
}


