package kafka_connect.brocker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonMessage {

    private int personId;
    private String fullName;
    private String email;
    private List<AddressMessage> addresses;
}
