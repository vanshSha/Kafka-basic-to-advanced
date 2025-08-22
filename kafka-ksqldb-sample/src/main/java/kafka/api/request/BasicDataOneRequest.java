package kafka.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicDataOneRequest {

    private boolean myBoolean;

    private String myString;

    private String myAnotherString;

    private int myInteger;

    private long myLong;

    private float myFloat;

    private double myDouble;

    private BigDecimal myBigDecimal;
}
