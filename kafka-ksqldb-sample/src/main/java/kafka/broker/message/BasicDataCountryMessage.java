package kafka.broker.message;

import lombok.Data;

@Data
public class BasicDataCountryMessage {

    private String countryName;
    private String currencyCode;
    private Integer population;

}
