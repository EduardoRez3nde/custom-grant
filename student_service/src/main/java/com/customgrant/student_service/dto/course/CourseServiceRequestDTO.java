package com.customgrant.student_service.dto.course;

public record CourseServiceRequestDTO(String requestId, String userId, int pageNumber, int pageSize) {

    public static CourseServiceRequestDTO of(final String requestId, final String userId, final int pageNumber, final int pageSize) {
        return new CourseServiceRequestDTO(requestId, userId, pageNumber, pageSize);
    }
}
