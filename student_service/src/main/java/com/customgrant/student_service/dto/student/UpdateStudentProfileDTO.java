package com.customgrant.student_service.dto.student;

import com.customgrant.student_service.entities.Student;

public record UpdateStudentProfileDTO(

        String firstName,
        String lastName,
        String photoProfileUrl,
        String biography,
        Boolean notificationsEnabled
) {
    public static UpdateStudentProfileDTO of(
            final String firstName,
            final String lastName,
            final String photoProfileUrl,
            final String biography,
            final Boolean notificationsEnabled
    ) {
        return new UpdateStudentProfileDTO(
                firstName,
                lastName,
                photoProfileUrl,
                biography,
                notificationsEnabled
        );
    }

    public  static UpdateStudentProfileDTO from(Student student) {
        return UpdateStudentProfileDTO.of(
                student.getFirstName(),
                student.getLastName(),
                student.getPhotoProfileUrl(),
                student.getBiography(),
                student.getNotificationsEnabled()
        );
    }
}
