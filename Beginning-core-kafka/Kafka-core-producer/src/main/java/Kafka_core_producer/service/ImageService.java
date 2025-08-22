package Kafka_core_producer.service;

import Kafka_core_producer.entity.Image;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ImageService {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public Image generateImage(String type){
        var name = "image-" + COUNTER.incrementAndGet();
        var size = ThreadLocalRandom.current().nextLong(1000, 10001);

        return new Image(name, size, type);
    }


}
