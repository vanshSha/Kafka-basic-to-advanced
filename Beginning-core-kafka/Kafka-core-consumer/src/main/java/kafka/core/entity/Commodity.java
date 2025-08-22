package kafka.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Commodity {

    private String name;
    private double price;
    private String measurement;
    private long timestamp;

    public Commodity(String name, double price, String measurement, long timestamp) {
        this.name = name;
        this.setPrice(price);
        this.measurement = measurement;
        this.timestamp = timestamp;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = Math.round(price * 100.0) / 100.0;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
