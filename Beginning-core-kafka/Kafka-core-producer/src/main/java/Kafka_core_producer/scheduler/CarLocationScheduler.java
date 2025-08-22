package Kafka_core_producer.scheduler;

import Kafka_core_producer.entity.CarLocation;
import Kafka_core_producer.producer.CarLocationProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class CarLocationScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(CarLocationScheduler.class);

    private CarLocation carLocationOne;
    private CarLocation carLocationTwo;
    private CarLocation carLocationThree;

    @Autowired
    private CarLocationProducer producer;

    public CarLocationScheduler(){
        var now = System.currentTimeMillis();

        carLocationOne = new CarLocation("car-1", now, 0);
        carLocationTwo = new CarLocation("car-2", now, 110);
        carLocationThree = new CarLocation("car-3", now, 95);
    }

    public void generateDummyData(){
        var now = System.currentTimeMillis();
        carLocationOne.setTimestamp(now);
        carLocationTwo.setTimestamp(now);
        carLocationThree.setTimestamp(now);

        carLocationOne.setDistance(carLocationOne.getDistance() + 1);
        carLocationTwo.setDistance(carLocationTwo.getDistance() - 1);
        carLocationThree.setDistance(carLocationThree.getDistance() + 1);

        sendCarLocation(carLocationOne);
        sendCarLocation(carLocationTwo);
        sendCarLocation(carLocationThree);

    }

    private void sendCarLocation(CarLocation carLocation){
        producer.sendCarLocation(carLocation);
        LOG.info("Sent car location: {}", carLocation);
    }

}
