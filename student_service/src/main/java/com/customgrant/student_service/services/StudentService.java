package com.customgrant.student_service.services;

import com.customgrant.student_service.dto.course.CourseServiceResponseDTO;
import com.customgrant.student_service.dto.student.StudentDTO;
import com.customgrant.student_service.dto.student.StudentProfileDTO;
import com.customgrant.student_service.dto.student.UpdateStudentProfileDTO;
import com.customgrant.student_service.entities.Student;
import com.customgrant.student_service.repositories.StudentRepository;
import com.customgrant.student_service.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

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

    @Transactional(readOnly = true)
    public StudentProfileDTO getStudentProfileByAuthUserId(final String userId) {

        final Student student = studentRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Resource not Found"));

        return StudentProfileDTO.from(student);
    }

    @Transactional
    public UpdateStudentProfileDTO updateStudentProfile(final String userId, final UpdateStudentProfileDTO updateDTO) {

        Student student = studentRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Resource not Found"));

        StudentService.updateFrom(student, updateDTO);
        student = studentRepository.save(student);

        return UpdateStudentProfileDTO.from(student);
    }

    public static void updateFrom(final Student student, final UpdateStudentProfileDTO dto) {
        if (dto.firstName() != null) student.setFirstName(dto.firstName());
        if (dto.lastName() != null) student.setLastName(dto.lastName());
        if (dto.photoProfileUrl() != null) student.setPhotoProfileUrl(dto.photoProfileUrl());
        if (dto.biography() != null) student.setBiography(dto.biography());
        if (dto.notificationsEnabled() != null) student.setNotificationsEnabled(dto.notificationsEnabled());
    }
}
