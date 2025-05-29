package com.rezende.course_service.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record ResponseListCourseDTO(String requestId, String userId, List<CourseDTO> course) {

    public static ResponseListCourseDTO of(final String requestId, final String userId, List<CourseDTO> courses) {
        return new ResponseListCourseDTO(requestId, userId, courses);
    }

    public static ResponseListCourseDTO valueOf(final String requestId, final String userId, final Page<CourseDTO> courses) {
        return new ResponseListCourseDTO(requestId, userId, courses.getContent());
    }

}
