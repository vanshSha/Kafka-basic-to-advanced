package Kafka_core_producer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    private String invoiceNumber;

    private double amount;

    private String currency;

}
