package com.rezende.course_service.dto;

import java.util.UUID;

public record StudentRequestDTO(String requestId, String userId) {

    public static StudentRequestDTO of(final String requestId, final UUID userId) {
        return new StudentRequestDTO(requestId, String.valueOf(userId));
    }
}
