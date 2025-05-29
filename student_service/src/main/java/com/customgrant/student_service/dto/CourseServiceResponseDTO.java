package com.customgrant.student_service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseServiceResponseDTO {

    private String requestId;
    private String userId;
    private final List<CourseDTO> courses = new ArrayList<>();

    public CourseServiceResponseDTO() { }

    private CourseServiceResponseDTO(String requestId, String userId, List<CourseDTO> courseDTOS) {
        this.requestId = requestId;
        this.userId = userId;
        this.courses.addAll(courseDTOS);
    }

    public static CourseServiceResponseDTO of(final String requestId, final String userId, final List<CourseDTO> courseDTOS) {
        return new CourseServiceResponseDTO(
                requestId,
                userId,
                courseDTOS.stream().map(CourseDTO::from).toList()
        );
    }

    public static class CourseDTO {

        private UUID id;
        private String name;
        private String description;

        public CourseDTO() { }

        private CourseDTO(UUID id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public static CourseDTO from(final CourseDTO courseDTO) {
            return new CourseDTO(courseDTO.getId(), courseDTO.getName(), courseDTO.getDescription());
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

        public List<CourseDTO> getCourses() {
        return courses;
    }
}
