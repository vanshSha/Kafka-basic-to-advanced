package kafka_connect.brocker.schema;

import kafka_connect.brocker.message.KafkaConnectSchema;
import kafka_connect.brocker.message.KafkaConnectSchema.Type;

public class KafkaConnectPersonAddressTargetKeySchema {

    private static KafkaConnectSchema instance;

    static {
        instance = new KafkaConnectSchema(Type.int32.toString(), false, null, null);
    }

    private KafkaConnectPersonAddressTargetKeySchema() {

    }

    public static KafkaConnectSchema instance() {
        return instance;
    }
}
