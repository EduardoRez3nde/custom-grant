package com.customgrant.student_service.configuration;

import com.customgrant.student_service.dto.course.CourseServiceRequestDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

// Configura como envia mensagens para o kafka

@Configuration
public class KafkaProducerConfig {

    @Bean
    public Map<String, Object> producerConfig() {

        Map<String, Object> properties = new HashMap<>();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return properties;
    }

    @Bean
    public ProducerFactory<String, CourseServiceRequestDTO> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }


    @Bean
    public KafkaTemplate<String, CourseServiceRequestDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
