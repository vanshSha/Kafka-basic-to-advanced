package kafka.api.request;

import lombok.Data;

@Data
public class BasicDataCountryRequest {

    private String countryName;
    private String currencyCode;
    private int population;
}
