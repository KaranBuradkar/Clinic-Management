package com.clinic.main.dtos;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String error;
    private String path;

    public ApiResponse() {
    }

    public ApiResponse(HttpStatus status, LocalDateTime timestamp, String error, String path) {
        this.status = status;
        this.timestamp = timestamp;
        this.error = error;
        this.path = path;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
