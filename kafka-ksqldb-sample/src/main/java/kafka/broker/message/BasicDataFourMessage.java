package kafka.broker.message;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BasicDataFourMessage {

    private String[] myStringArray;

    private List<Integer> myIntegerList;

    private Set<Double> myDoubleSet;
}
