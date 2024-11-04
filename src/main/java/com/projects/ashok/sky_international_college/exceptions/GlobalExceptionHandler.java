package com.projects.ashok.sky_international_college.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Builds a structured error response.
     *
     * @param status  the status of the error (e.g., "error")
     * @param title   the title of the error (e.g., "Data Not Found")
     * @param message the message describing the error
     * @param details any additional details for the error response
     * @return a Map containing the error response structure
     */
    private Map<String, Object> buildErrorResponse(String status, String title, String message, Map<String, ?> details) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("title", title);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", LocalDateTime.now().format(FORMATTER));
        if (details != null && !details.isEmpty()) {
            errorResponse.put("details", details);
        }
        return errorResponse;
    }

    /**
     * Handles ResourceNotFoundException and returns a detailed error response.
     *
     * @param exception the ResourceNotFoundException that was thrown
     * @return a ResponseEntity containing the error details and HTTP status code
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        Map<String, String> details = new HashMap<>();
        details.put("resource", exception.getResourceName());
//        details.put("query", exception.getFieldName() + ": " + exception.getFieldValue());

        Map<String, Object> errorResponse = buildErrorResponse(
                "error", "Data Not Found", exception.getMessage(), details);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles HttpMessageNotReadableException and returns a detailed error response.
     *
     * @param notReadableException the HttpMessageNotReadableException that was thrown
     * @return a ResponseEntity containing the error details and HTTP status code
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException notReadableException) {
        Map<String, Object> errorResponse = buildErrorResponse(
                "error", "Invalid Input", "Invalid data format received: " + notReadableException.getMessage(), null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a detailed error response.
     *
     * @param validationException the MethodArgumentNotValidException that was thrown
     * @return a ResponseEntity containing the error details and HTTP status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException validationException) {
        Map<String, String> errorMessages = new HashMap<>();
        BindingResult result = validationException.getBindingResult();

        for (FieldError error : result.getFieldErrors()) {
            errorMessages.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> errorResponse = buildErrorResponse(
                "error", "Invalid Input", "Validation failed for one or more fields.", errorMessages);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
