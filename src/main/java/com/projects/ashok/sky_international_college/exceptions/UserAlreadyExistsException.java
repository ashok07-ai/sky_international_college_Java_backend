package com.projects.ashok.sky_international_college.exceptions;

import java.util.Map;

public class UserAlreadyExistsException extends RuntimeException{
    private final Map<String, String> errorDetails;

    public UserAlreadyExistsException(String message, Map<String, String> errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

    public Map<String, String> getErrorDetails() {
        return errorDetails;
    }
}
