package kafka_ms_order.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String orderLocation;

    private String creditCardNumber;

    private List<OrderItemRequest> items;
}
