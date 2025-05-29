package com.rezende.course_service.configuration;

import com.rezende.course_service.dto.StudentRequestDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String defaultGroupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetResetConfig;

    @Bean
    public ConsumerFactory<String, StudentRequestDTO> requestConsumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, defaultGroupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);

        final JsonDeserializer<StudentRequestDTO> jsonDeserializer = new JsonDeserializer<>(StudentRequestDTO.class);
        jsonDeserializer.setUseTypeHeaders(false);
        jsonDeserializer.addTrustedPackages("com.rezende.course_service.dto", "*");

        final ErrorHandlingDeserializer<StudentRequestDTO> errorHandlingValueDeserializer =
                new ErrorHandlingDeserializer<>(jsonDeserializer);

        final StringDeserializer stringKeyDeserializer = new StringDeserializer();

        return new DefaultKafkaConsumerFactory<>(
                props,
                stringKeyDeserializer,
                errorHandlingValueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StudentRequestDTO> requestKafkaListenerContainerFactory(
            ConsumerFactory<String, StudentRequestDTO> requestConsumerFactory
    ) {
        final ConcurrentKafkaListenerContainerFactory<String, StudentRequestDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(requestConsumerFactory);
        return factory;
    }
}