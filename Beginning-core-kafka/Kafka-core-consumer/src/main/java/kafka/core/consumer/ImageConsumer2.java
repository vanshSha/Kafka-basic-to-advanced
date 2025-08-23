package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.core.entity.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

//@Service
public class ImageConsumer2 {

    private static final Logger LOG = LoggerFactory.getLogger(ImageConsumer2.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RetryableTopic(
            autoCreateTopics = "true",
            attempts = "4",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_DELAY_VALUE,
            backoff = @Backoff(
                    delay = 3000,
                    maxDelay = 10000,
                    multiplier = 1.5,
                    random = true
            ),
            dltTopicSuffix = "-dead"
    )
    @KafkaListener(topics = "t-image2", concurrency = "2")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws Exception{
    Image image = objectMapper.readValue(message, Image.class);
    if("svg".equalsIgnoreCase(image.getType())){
        LOG.warn("Image type is SVG : {}" , image);
        throw new IllegalArgumentException("SVG images are not allowed");
    }
    LOG.info("Consumed message: {} from partition: {}", message, partition);
    }

}
