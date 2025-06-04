package com.customgrant.student_service.configuration;

import com.customgrant.student_service.dto.course.CourseServiceResponseDTO;
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


// Configura como a aplicação recebe mensagens do Kafka

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.student-course-response}")
    private String defaultGroupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetResetConfig;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackage;

    @Bean
    public ConsumerFactory<String, CourseServiceResponseDTO> requestConsumerFactory() {

        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, defaultGroupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);

        final JsonDeserializer<CourseServiceResponseDTO> jsonDeserializer = new JsonDeserializer<>(CourseServiceResponseDTO.class);
        jsonDeserializer.setUseTypeHeaders(false);
        jsonDeserializer.addTrustedPackages("com.customgrant.student_service.dto", "*");

        final ErrorHandlingDeserializer<CourseServiceResponseDTO> errorHandlingValueDeserializer =
                new ErrorHandlingDeserializer<>(jsonDeserializer);

        final StringDeserializer stringKeyDeserializer = new StringDeserializer();

        return new DefaultKafkaConsumerFactory<>(
                props,
                stringKeyDeserializer,
                errorHandlingValueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CourseServiceResponseDTO> requestKafkaListenerContainerFactory(
            ConsumerFactory<String, CourseServiceResponseDTO> requestConsumerFactory
    ) {
        final ConcurrentKafkaListenerContainerFactory<String, CourseServiceResponseDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(requestConsumerFactory);
        return factory;
    }
}