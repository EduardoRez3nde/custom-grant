package com.customgrant.student_service.configuration.event;

import com.customgrant.student_service.dto.student.StudentDTO;
import org.springframework.context.ApplicationEvent;

public class ListAvailableCoursesEvent extends ApplicationEvent {

    private final StudentDTO loginInfo;
    private final int pageNumber;
    private final int pageSize;

    public ListAvailableCoursesEvent(final Object source, final StudentDTO loginInfo, int pageNumber, int pageSize) {
        super(source);
        this.loginInfo = loginInfo;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public StudentDTO getLoginInfo() {
        return loginInfo;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}
