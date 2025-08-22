package kafka.broker.message;

import lombok.Data;

@Data
public class BasicDataAddressMessage {

    private String streetAddress;
    private String country;
    private BasicDataLocationMessage location;

}
