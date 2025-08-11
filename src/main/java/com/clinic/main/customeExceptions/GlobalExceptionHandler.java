package com.clinic.main.customeExceptions;

import com.clinic.main.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiResponse> patientNotFoundException(PatientNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), message, exception.getClass().toString());
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ApiResponse> doctorNotFoundException(DoctorNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), message, exception.getClass().toString());
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ApiResponse> appointmentNotFoundException(AppointmentNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), message, exception.getClass().toString());
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException exception) {
        String message = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), message, exception.getClass().toString());
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
