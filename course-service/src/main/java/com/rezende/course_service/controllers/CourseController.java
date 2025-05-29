package com.rezende.course_service.controllers;

import com.rezende.course_service.dto.CourseDTO;
import com.rezende.course_service.services.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> findAll(final Pageable pageable) {
        final Page<CourseDTO> courses = courseService.findAll(pageable);
        return ResponseEntity.ok(courses);
    }
}
