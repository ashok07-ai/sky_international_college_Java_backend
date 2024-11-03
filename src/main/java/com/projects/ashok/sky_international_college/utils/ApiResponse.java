package com.projects.ashok.sky_international_college.utils;

public class ApiResponse<T> {
    private String message;
    private T responseObject;

    /**
     * Constructs an ApiResponse with the specified message and response object.
     *
     * @param message a descriptive message about the API response
     * @param responseObject the object returned by the API
     */
    public ApiResponse(String message, T responseObject) {
        this.message = message;
        this.responseObject = responseObject;
    }

    // Getters and Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }
}
