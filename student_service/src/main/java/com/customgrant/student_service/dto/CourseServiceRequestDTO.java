package com.customgrant.student_service.dto;

import java.util.UUID;

public record CourseServiceRequestDTO(String requestId, String userId) {

    public static CourseServiceRequestDTO of(final String requestId, final UUID userId) {
        return new CourseServiceRequestDTO(requestId, String.valueOf(userId));
    }
}
