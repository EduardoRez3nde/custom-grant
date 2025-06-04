package com.customgrant.custom_grant.configuration.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder
                        .name("UserRegisteredEvent")
                        .partitions(1)
                        .replicas(1)
                        .compact()
                        .build()
        );
    }
}
