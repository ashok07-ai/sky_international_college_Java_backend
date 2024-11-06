package com.projects.ashok.sky_international_college.exceptions;

import com.projects.ashok.sky_international_college.utils.ErrorApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    /**
     * Builds a structured error response wrapped in an "error" object.
     *
     * @param status  the status of the error (e.g., "error")
     * @param title   the title of the error (e.g., "Invalid Input")
     * @param message the main error message or a map of detailed error messages
     * @param details additional error details if available
     * @return a Map containing the error response structure
     */
    public Map<String, Object> buildErrorResponse(String status, String title, String message, Map<String, ?> details) {
        Map<String, Object> errorContent = new HashMap<>();

        errorContent.put("status", status);
        errorContent.put("title", title);
        errorContent.put("timestamp", LocalDateTime.now().format(FORMATTER));

        // Wrap message as a single message or as detailed message map if provided
        if (details != null && !details.isEmpty()) {
            errorContent.put("message", details);
        } else {
            errorContent.put("message", Map.of("message", message));
        }

        // Wrap everything in "error" key as requested
        return Map.of("error", errorContent);
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
        details.put("query", exception.getFieldName() + ": " + exception.getFieldValue());

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

    /**
     * Handles exceptions thrown when a user already exists (e.g., when attempting to register with an already used email or username).
     * This method intercepts the `UserAlreadyExistsException`, extracts the error details (such as the specific field and error message),
     * and constructs a structured error response to return to the client.
     *
     * @param ex The `UserAlreadyExistsException` containing the details of the error (e.g., field name and error message).
     * @return A `ResponseEntity` containing a Map with a key `"error"`, which holds an `ErrorApiResponse` object
     *         that includes the error details, status, and timestamp, along with an HTTP status code of 409 (CONFLICT).
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, ErrorApiResponse>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        // Retrieve the error details (field name and message) from the exception
        Map<String, String> errorDetails = ex.getErrorDetails() != null ? ex.getErrorDetails() : Map.of();

        // Create the ErrorApiResponse with status "error"
        ErrorApiResponse response = new ErrorApiResponse(errorDetails, "error");

        // Wrap the ErrorApiResponse in a Map with "error" as the key
        Map<String, ErrorApiResponse> errorResponse = Map.of("error", response);

        // Return the ResponseEntity with the error response and HttpStatus.CONFLICT (409)
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handle BadCredentialsException thrown during login attempt.
     * This exception is thrown when invalid credentials are provided.
     *
     * @return ResponseEntity with custom error message and details.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException() {
        // Create the error message map for the invalid login credentials
        Map<String, String> message = Map.of("message", "Invalid login credentials.");

        // Create the complete error response map, including the status and timestamp
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", message);  // The error message
        errorResponse.put("status", "error");  // The status indicating an error
        errorResponse.put("timestamp", System.currentTimeMillis());  // The current timestamp in milliseconds

        // Return the custom error response with HTTP 400 (BAD_REQUEST) status
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
