package kafka_connect.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String TOPIC_DEMO_BINARY = "t-demo-binary";

    @Bean
    NewTopic demoBinaryTopic() {
        return TopicBuilder.name(TOPIC_DEMO_BINARY).partitions(1).replicas(1).build();
    }
}
