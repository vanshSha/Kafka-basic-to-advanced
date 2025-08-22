package kafka.broker.message;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BasicDataOneMessage {

    private boolean myBoolean;

    private String myString;

    private String myAnotherString;

    private int myInteger;

    private long myLong;

    private float myFloat;

    private double myDouble;

    private BigDecimal myBigDecimal;

}
