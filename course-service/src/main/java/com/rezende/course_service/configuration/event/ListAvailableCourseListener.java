package com.rezende.course_service.configuration.event;

import com.rezende.course_service.dto.ResponseListCourseDTO;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ListAvailableCourseListener {

    private final KafkaTemplate<String, ResponseListCourseDTO> kafkaTemplate;

    public ListAvailableCourseListener(final KafkaTemplate<String, ResponseListCourseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void onListAvailableCourse(final ListAvailableCoursesEvent event) {

        final ResponseListCourseDTO payload = event.getResponseListCourseInfo();

        kafkaTemplate.send("response-list-available-courses", payload.requestId(), payload);

        System.out.println("✅ Mensagem enviada para Kafka: " + event.getResponseListCourseInfo());
    }
}
