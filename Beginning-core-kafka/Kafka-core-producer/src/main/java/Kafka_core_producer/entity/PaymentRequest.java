package Kafka_core_producer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private int amount;

    private String currency;

    private String bankAccountNumber;

    private String note;

    private LocalDate paymentDate;


}
