package com.rezende.course_service.dto;

import com.rezende.course_service.entities.Course;

import java.util.Objects;
import java.util.UUID;

public class CourseDTO {

    private UUID id;
    private String name;
    private String description;

    public CourseDTO() { }

    private CourseDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static CourseDTO of(final UUID id, final String name, final String description) {
        return new CourseDTO(id, name, description);
    }

    public static CourseDTO from(final Course course) {
        return CourseDTO.of(course.getId(), course.getName(), course.getDescription());
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return Objects.equals(getId(), courseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
