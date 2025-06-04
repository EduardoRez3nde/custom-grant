package com.customgrant.student_service.services.exceptions;

import com.customgrant.student_service.exceptions.NoStackTraceException;

public class ResourceNotFoundException extends NoStackTraceException {


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
