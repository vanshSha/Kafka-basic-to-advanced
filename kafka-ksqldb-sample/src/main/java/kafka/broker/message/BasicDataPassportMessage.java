package kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BasicDataPassportMessage {

    private String number;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
}
