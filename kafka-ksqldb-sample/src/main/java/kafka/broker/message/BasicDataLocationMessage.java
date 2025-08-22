package kafka.broker.message;

import lombok.Data;

@Data
public class BasicDataLocationMessage {

    private double latitude;
    private double longitude;
}
