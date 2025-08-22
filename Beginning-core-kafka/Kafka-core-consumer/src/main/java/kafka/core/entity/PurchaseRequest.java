package kafka.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    private UUID requestId;

    private String requestNumber;

    private int amount;

    private String currency;



}
