package com.rezende.course_service.dto;

import com.rezende.course_service.entities.Course;
import org.springframework.data.domain.Page;

import java.util.List;

public record ResponseListCourseDTO(String requestId, String userId, List<CourseDTO> course) {

    public static ResponseListCourseDTO of(final String requestId, final String userId, List<Course> courses) {
        final List<CourseDTO> courseDTOS = courses.stream()
                .map(CourseDTO::from)
                .toList();
        return new ResponseListCourseDTO(requestId, userId, courseDTOS);
    }

    public static ResponseListCourseDTO valueOf(final String requestId, final String userId, final Page<CourseDTO> courses) {
        return new ResponseListCourseDTO(requestId, userId, courses.getContent());
    }

}
