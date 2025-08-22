package kafka.broker.message;

import lombok.Data;

@Data
public class BasicDataTwoMessage {

    private long myEpochDay;

    private long myMillisOfDay;

    private long myEpochMillis;
}
