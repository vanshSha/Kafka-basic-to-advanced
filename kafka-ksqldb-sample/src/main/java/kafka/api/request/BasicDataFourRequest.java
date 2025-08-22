package kafka.api.request;

import lombok.Data;

@Data
public class BasicDataFourRequest {

    private int arrayElementsCount;
    private int listElementsCount;
    private int setElementsCount;


}
