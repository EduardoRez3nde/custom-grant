package com.customgrant.student_service.controllers;

import com.customgrant.student_service.dto.course.CourseServiceResponseDTO;
import com.customgrant.student_service.dto.student.StudentDTO;
import com.customgrant.student_service.dto.student.StudentProfileDTO;
import com.customgrant.student_service.dto.student.UpdateStudentProfileDTO;
import com.customgrant.student_service.services.StudentCourseCommunicationService;
import com.customgrant.student_service.services.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentService studentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentCourseCommunicationService communicationService;

    public StudentController(StudentService studentService, StudentCourseCommunicationService communicationService) {
        this.studentService = studentService;
        this.communicationService = communicationService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable final UUID id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> findAll(final Pageable pageable) {
        return ResponseEntity.ok(studentService.findAll(pageable));
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentProfileDTO> getStudentProfileByAuthUserId(final @AuthenticationPrincipal Jwt jwt) {
        final String userId = jwt.getSubject();
        final StudentProfileDTO studentProfile = studentService.getStudentProfileByAuthUserId(userId);
        return ResponseEntity.ok(studentProfile);
    }

    @PutMapping
    public ResponseEntity<UpdateStudentProfileDTO> updateStudentProfile(
            final @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody final UpdateStudentProfileDTO updateProfile
    ) {
            final String userId = jwt.getSubject();
            final UpdateStudentProfileDTO studentProfile = studentService.updateStudentProfile(userId, updateProfile);

            return ResponseEntity.ok(studentProfile);
    }


    @GetMapping("/available-courses")
    public ResponseEntity<?> findAvailableCourses(
            @RequestParam(defaultValue = "userId", required = true) String userId,
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        final CompletableFuture<CourseServiceResponseDTO> future =
                communicationService.requestAvailableCourses(userId, pageNumber, pageSize);

        try {
            final CourseServiceResponseDTO fullResponse = future.get(35, TimeUnit.SECONDS);

            if (fullResponse == null) {
                LOGGER.warn("STUDENT-SERVICE: Resposta do CourseService foi nula para UserId: {}", userId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Resposta inesperada do serviço de cursos.");
            }
            final List<CourseServiceResponseDTO.CourseDTO> courses = future.get().getCourses();

            LOGGER.info("STUDENT-SERVICE: RESPOSTA RECEBIDA DO COURSE-SERVICE PARA USERID: {}. {} CURSOS ENCONTRADOS", userId, courses);

            return ResponseEntity.ok(courses);

        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao processar a solicitação de cursos: " + e.getMessage());
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
