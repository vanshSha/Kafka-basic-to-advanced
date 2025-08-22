package kafka_ms_order.broker.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountMessage {

    private String discountCode;

    private int discountPercentage;
}
