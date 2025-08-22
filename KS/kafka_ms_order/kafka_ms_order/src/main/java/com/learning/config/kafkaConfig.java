package com.learning.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class kafkaConfig {

    //@Bean
    NewTopic topicCommodityOrder(){
        return TopicBuilder.name("t-commodity-order")
                .partitions(3)
                .build();
    }

    @Bean
    NewTopic topicCommodityOrderReply() {
        return TopicBuilder.name("t-commodity-order-reply")
                .partitions(1)
                .build();
    }

    @Bean
    NewTopic topicCommodityPromotion() {
        return TopicBuilder.name("t-commodity-promotion")
                .partitions(1)
                .build();
    }

}
