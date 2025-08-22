package Kafka_core_producer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarLocation {

    private String carId;

    private double timestamp;

    private int distance;

}
