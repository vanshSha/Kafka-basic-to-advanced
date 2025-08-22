package kafka.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Data
public class BasicDataPersonRequest {

    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Map<String, String> contacts;
    private Passport passport;
    private List<Address> addresses;

    @Data
    public static class Address {

        private String streetAddress;
        private String country;
        private Location location;
    }

    @Data
    public static class Location {
        private double latitude;
        private double longitude;
    }

    @Data
    public static class Passport {

        private String number;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate expirationDate;
    }

}
