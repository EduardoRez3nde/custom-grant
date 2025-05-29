package com.customgrant.student_service.configuration.event;

import com.customgrant.student_service.dto.CourseServiceRequestDTO;
import com.customgrant.student_service.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


// Ouvi e Reage a eventos que acontece na aplicação

@Service
public class ListAvailableCourseListener {

    @Value("${spring.kafka.topic.request-course-available-courses}")
    private String requestTopic;

    private final KafkaTemplate<String, CourseServiceRequestDTO> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListAvailableCourseListener.class);

    public ListAvailableCourseListener(final KafkaTemplate<String, CourseServiceRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void onListAvailableCourse(final ListAvailableCoursesEvent event) {

        final StudentDTO student = event.getLoginInfo();
        final String requestId = UUID.randomUUID().toString();

        final int pageNumber = event.getPageNumber();
        final int pageSize = event.getPageSize();
        final String id = String.valueOf(student.getId());

        final CourseServiceRequestDTO payload = CourseServiceRequestDTO.of(requestId, id, pageNumber, pageSize);

        LOGGER.info("STUDENT-SERVICE: Enviando para o kafka np tópico '{}'. RequestId: {}, Payload: {}", requestTopic, requestId, payload);

        kafkaTemplate.send("list-available-courses", requestId, payload);

        System.out.println("✅ Mensagem enviada para Kafka: " + event.getLoginInfo());
    }
}
