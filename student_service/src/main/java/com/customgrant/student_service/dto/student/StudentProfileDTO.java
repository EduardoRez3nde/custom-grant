package com.customgrant.student_service.dto.student;

import com.customgrant.student_service.entities.Student;

import java.time.Instant;

public record StudentProfileDTO(

        String firstName,
        String lastName,
        Instant lastActivity,
        String photoProfileUrl,
        String biography,
        Boolean notificationsEnabled,
        Boolean active,
        Instant createdAt,
        Instant updatedAt

) {
    public static StudentProfileDTO of(
            final String firstName,
            final String lastName,
            final Instant lastActivity,
            final String photoProfileUrl,
            final String biography,
            final Boolean notificationsEnabled,
            final Boolean active,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new StudentProfileDTO(
                firstName,
                lastName,
                lastActivity,
                photoProfileUrl,
                biography,
                notificationsEnabled,
                active,
                createdAt,
                updatedAt
        );
    }

    public static StudentProfileDTO from(Student student) {
        return StudentProfileDTO.of(
                student.getFirstName(),
                student.getLastName(),
                student.getLastActivity(),
                student.getPhotoProfileUrl(),
                student.getBiography(),
                student.getNotificationsEnabled(),
                student.getIsActive(),
                student.getCreatedAt(),
                student.getUpdateAt()
        );
    }
}
