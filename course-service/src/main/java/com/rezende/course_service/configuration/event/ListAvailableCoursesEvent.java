package com.rezende.course_service.configuration.event;

import com.rezende.course_service.dto.ResponseListCourseDTO;
import org.springframework.context.ApplicationEvent;

public class ListAvailableCoursesEvent extends ApplicationEvent {

    private final ResponseListCourseDTO responseListCourse;

    public ListAvailableCoursesEvent(final Object source, final ResponseListCourseDTO responseListCourse) {
        super(source);
        this.responseListCourse = responseListCourse;
    }

    public ResponseListCourseDTO getResponseListCourseInfo() {
        return responseListCourse;
    }
}
