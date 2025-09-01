package kafka_connect;

import kafka_connect.brocker.producer.BinaryAsBased64Producer;
import kafka_connect.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.io.File;

@SpringBootApplication
//@EnableKafkaStreams
// implements CommandLineRunner
public class KafkaConnectSampleApplication  {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConnectSampleApplication.class, args);
    }

//    @Autowired
//    private BinaryAsBased64Producer producer;
//
//    @Override
//    public void run(String... args) throws Exception {
//		File file = new File("src/main/resources/RK098_5.jpg.webp");
//
//        producer.send(KafkaConfig.TOPIC_DEMO_BINARY, file);
//    }
}
