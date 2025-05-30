package com.rezende.course_service.dto;

import java.util.UUID;

public record StudentRequestDTO(String requestId, String userId, Integer pageNumber, Integer pageSize) {

    public static StudentRequestDTO of(final String requestId, final UUID userId, final int pageNumber, final int pageSize) {
        return new StudentRequestDTO(requestId, String.valueOf(userId), pageNumber, pageSize);
    }
}
