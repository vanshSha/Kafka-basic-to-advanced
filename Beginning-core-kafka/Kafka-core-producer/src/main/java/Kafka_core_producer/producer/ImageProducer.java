package Kafka_core_producer.producer;

import Kafka_core_producer.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendImageToPartition(Image image, int partition){
        try{
            var imageJson = objectMapper.writeValueAsString(image);
            kafkaTemplate.send("t-image", partition, image.getType(), imageJson);
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
