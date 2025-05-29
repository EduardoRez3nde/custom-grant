package com.customgrant.student_service.configuration.event;

import com.customgrant.student_service.dto.CourseServiceRequestDTO;
import com.customgrant.student_service.dto.StudentDTO;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ListAvailableCourseListener {

    private final KafkaTemplate<String, CourseServiceRequestDTO> kafkaTemplate;

    public ListAvailableCourseListener(final KafkaTemplate<String, CourseServiceRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void onListAvailableCourse(final ListAvailableCoursesEvent event) {

        final StudentDTO student = event.getLoginInfo();
        final String requestId = UUID.randomUUID().toString();

        final CourseServiceRequestDTO payload = CourseServiceRequestDTO.of(requestId, student.getId());

        kafkaTemplate.send("list-available-courses", requestId, payload);

        System.out.println("✅ Mensagem enviada para Kafka: " + event.getLoginInfo());
    }
}
