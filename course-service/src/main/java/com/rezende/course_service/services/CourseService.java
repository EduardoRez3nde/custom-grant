package com.rezende.course_service.services;

import com.rezende.course_service.configuration.event.ListAvailableCoursesEvent;
import com.rezende.course_service.dto.CourseDTO;
import com.rezende.course_service.dto.ResponseListCourseDTO;
import com.rezende.course_service.dto.StudentRequestDTO;
import com.rezende.course_service.entities.Course;
import com.rezende.course_service.repositories.CourseRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CourseService(CourseRepository courseRepository, ApplicationEventPublisher eventPublisher) {
        this.courseRepository = courseRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public Page<CourseDTO> findAll(final Pageable pageable) {
        final Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(CourseDTO::from);
    }

    @Transactional(readOnly = true)
    public Page<CourseDTO> findAllAvailableCourse(final Pageable pageable) {

        // TODO: Implementar logica para buscar cursos disponiveis.

        final Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(CourseDTO::from);
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.request-course-from-student}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "requestKafkaListenerContainerFactory"
    )
    public void consumeStudentCourseRequest(@Payload final StudentRequestDTO studentRequest) {

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<CourseDTO> availableCourses = findAllAvailableCourse(pageable);

        final ResponseListCourseDTO responseCourses = ResponseListCourseDTO.valueOf(
                studentRequest.requestId(),
                studentRequest.userId(),
                availableCourses
        );
        final ListAvailableCoursesEvent event = new ListAvailableCoursesEvent(this, responseCourses);
        eventPublisher.publishEvent(event);
    }
}

