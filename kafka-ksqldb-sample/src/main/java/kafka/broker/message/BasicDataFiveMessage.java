package kafka.broker.message;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class BasicDataFiveMessage {

    private Map<Integer, String> myMapAlpha;

    private Map<UUID, String> myMapBeta;
}
