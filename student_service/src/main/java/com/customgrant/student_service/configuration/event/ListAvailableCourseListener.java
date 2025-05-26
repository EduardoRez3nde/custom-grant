package com.customgrant.student_service.configuration.event;

import com.customgrant.student_service.dto.StudentDTO;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ListAvailableCourseListener {

    private final KafkaTemplate<String, StudentDTO> kafkaTemplate;


    public ListAvailableCourseListener(final KafkaTemplate<String, StudentDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void onListAvailableCourse(final ListAvailableCoursesEvent event) {

        final StudentDTO student = event.getLoginInfo();

        final StudentDTO payload = new StudentDTO.Builder()
                .id(student.getId())
                .build();

        final String requestId = UUID.randomUUID().toString();

        kafkaTemplate.send("list-available-courses", requestId, payload);

        System.out.println("✅ Mensagem enviada para Kafka: " + event.getLoginInfo());
    }
}
