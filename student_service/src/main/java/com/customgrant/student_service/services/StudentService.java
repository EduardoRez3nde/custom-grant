package com.customgrant.student_service.services;

import com.customgrant.student_service.dto.CourseServiceResponseDTO;
import com.customgrant.student_service.dto.StudentDTO;
import com.customgrant.student_service.entities.Student;
import com.customgrant.student_service.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final Map<String,  CompletableFuture<CourseServiceResponseDTO>> pendingRequests;

    public StudentService(StudentRepository studentRepository, Map<String, CompletableFuture<CourseServiceResponseDTO>> pendingRequests1) {
        this.studentRepository = studentRepository;
        this.pendingRequests = pendingRequests1;
    }

    @Transactional(readOnly = true)
    public StudentDTO findById(final UUID id) {

        final Student student = studentRepository.findById(id).orElseThrow();

        return new StudentDTO.Builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(final Pageable pageable) {

        final Page<Student> students = studentRepository.findAll(pageable);

        return students.map(student ->
                new StudentDTO.Builder()
                        .id(student.getId())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .photoProfileUrl(student.getPhotoProfileUrl())
                        .build()
        );
    }
}
