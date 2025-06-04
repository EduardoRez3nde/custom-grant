package com.customgrant.student_service.services;

import com.customgrant.student_service.dto.course.CourseServiceResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


// Receber e processar a resposta

@Service
public class CourseResponseConsumer {

    private final StudentCourseCommunicationService communicationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseResponseConsumer.class);

    public CourseResponseConsumer(StudentCourseCommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.response-course-available-courses}",
            groupId = "${spring.kafka.consumer.group-id.student-course-response}",
            containerFactory = "requestKafkaListenerContainerFactory"
    )
    public void consumerCourseResponse(
            @Payload CourseServiceResponseDTO response,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        LOGGER.info("STUDENT-SERVICE: RESPOSTA DE CURSO RECEBIDA DO COURSE-SERVICE");
        LOGGER.info("RequestId: {}", key);
        LOGGER.info("Payload: {}", response);

        if (key == null) {
            LOGGER.info("STUDENT-SERVICE: KEY NÃO ENCONTRADA");
            return;
        }

        communicationService.completeCourseRequest(key, response);

        if (response.getCourses() != null && !response.getCourses().isEmpty())
            LOGGER.info("STUDENT-SERVICE: Cursos para requestId {} foram recebidos e o CompletableFuture foi completado.", key);
        else {
            LOGGER.info("STUDENT-SERVICE: Nenhum curso recebido para requestId {}. O CompletableFuture foi completado com uma resposta vazia (ou poderia ser completado com erro se apropriado).", key);
            communicationService.failCourseRequest(response.getRequestId(), new Throwable());
        }
    }
}
