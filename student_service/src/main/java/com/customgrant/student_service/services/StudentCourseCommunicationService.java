package com.customgrant.student_service.services;

import com.customgrant.student_service.dto.CourseServiceRequestDTO;
import com.customgrant.student_service.dto.CourseServiceResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/*

    * Inicia a requisição no metodo requestAvailableCourses usando KafkaTemplate para enviar a mensagem
    * Usa CompletableFuture para obter a resposta
    * O completeCourseRequest quando uma resposta bem sucedida
    e failCourseRequest uma requisição específica falhou

*/

@Service
public class StudentCourseCommunicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseCommunicationService.class);

    private final KafkaTemplate<String, CourseServiceRequestDTO> kafkaTemplate;

    @Value("${spring.kafka.topic.request-course-available-courses}")
    private String requestTopic;

    private final Map<String, CompletableFuture<CourseServiceResponseDTO>> pendingCourseRequests;

    public StudentCourseCommunicationService(
            KafkaTemplate<String, CourseServiceRequestDTO> kafkaTemplate,
            Map<String, CompletableFuture<CourseServiceResponseDTO>> pendingCourseRequests
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.pendingCourseRequests = pendingCourseRequests;
    }

    public CompletableFuture<CourseServiceResponseDTO> requestAvailableCourses(final String userId, final int pageNumber, final int pageSize) {

        final String requestId = UUID.randomUUID().toString();
        final CourseServiceRequestDTO requestPayload = CourseServiceRequestDTO.of(requestId, userId, pageNumber, pageSize);

        final CompletableFuture<CourseServiceResponseDTO> future = new CompletableFuture<>();
        pendingCourseRequests.put(requestId, future);

        LOGGER.info("STUDENT-SERVICE: Enviando solicitação de cursos para o Kafka. RequestId: {}, UserId: {}", requestId, userId);
        kafkaTemplate.send(requestTopic, requestId, requestPayload);

        future.orTimeout(30, TimeUnit.SECONDS).whenComplete((response, throwable) -> {
            pendingCourseRequests.remove(requestId);
            if (throwable != null) {
                LOGGER.warn("STUDENT-SERVICE: Requisição de cursos {} falhou ou teve timeout: {}", requestId, throwable.getMessage());
            }
        });

        return future;
    }

    public void completeCourseRequest(final String requestId, final CourseServiceResponseDTO response) {
        final CompletableFuture<CourseServiceResponseDTO> future = pendingCourseRequests.get(requestId);
        if (future != null && !future.isDone()) {
            LOGGER.info("STUDENT-SERVICE: Completando future para requestId: {}", requestId);
            future.complete(response);
        } else {
            LOGGER.warn("STUDENT-SERVICE: Nenhum CompletableFuture pendente encontrado para requestId {} (resposta pode ter chegado tarde ou ID incorreto).", requestId);
        }
    }

    public void failCourseRequest(final String requestId, final Throwable throwable) {
        final CompletableFuture<CourseServiceResponseDTO> future = pendingCourseRequests.get(requestId);
        if (future != null && !future.isDone()) {
            LOGGER.warn("STUDENT-SERVICE: Falhando future para requestId: {}", requestId, throwable);
            future.completeExceptionally(throwable);
        }
    }
}