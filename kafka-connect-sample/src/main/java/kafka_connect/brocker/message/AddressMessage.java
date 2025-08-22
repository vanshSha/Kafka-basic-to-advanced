package kafka_connect.brocker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressMessage {

    private int addressId;
    private String address;
    private String city;
    private String postalCode;

}
