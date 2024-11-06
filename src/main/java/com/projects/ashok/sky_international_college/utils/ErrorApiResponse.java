package com.projects.ashok.sky_international_college.utils;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorApiResponse {
    private Map<String, String> message;
    private String status;
    private LocalDateTime timestamp;

    public ErrorApiResponse(Map<String, String> message, String status){
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
