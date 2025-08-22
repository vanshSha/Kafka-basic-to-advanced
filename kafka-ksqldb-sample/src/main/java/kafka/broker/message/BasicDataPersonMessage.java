package kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class BasicDataPersonMessage {

    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Map<String, String> contacts;
    private BasicDataPassportMessage passport;
    private List<BasicDataAddressMessage> addresses;
}
