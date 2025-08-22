package kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Data
public class BasicDataThreeMessage {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate myLocalDate;

    @JsonFormat(pattern = "dd MMM yyyy")
    private LocalDate myLocalDateCustomFormat;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime myLocalTime;

    @JsonFormat(pattern = "hh:mm:ss a")
    private LocalTime myLocalTimeCustomFormat;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime myLocalDateTime;

    @JsonFormat(pattern = "dd-MMM-yyyy hh:mm:ss a")
    private LocalDateTime myLocalDateTimeCustomFormat;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private OffsetDateTime myOffsetDateTime;

    @JsonFormat(pattern = "dd-MMM-yyyy hh:mm:ss.SSS a Z")
    private OffsetDateTime myOffsetDateTimeCustomFormat;
}
