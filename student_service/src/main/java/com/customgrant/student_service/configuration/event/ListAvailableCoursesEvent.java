package com.customgrant.student_service.configuration.event;

import com.customgrant.student_service.dto.StudentDTO;
import org.springframework.context.ApplicationEvent;

public class ListAvailableCoursesEvent extends ApplicationEvent {

    private final StudentDTO loginInfo;

    public ListAvailableCoursesEvent(final Object source, final StudentDTO loginInfo) {
        super(source);
        this.loginInfo = loginInfo;
    }

    public StudentDTO getLoginInfo() {
        return loginInfo;
    }
}
