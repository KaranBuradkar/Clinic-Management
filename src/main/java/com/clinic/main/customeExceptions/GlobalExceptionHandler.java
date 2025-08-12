package com.clinic.main.customeExceptions;

import com.clinic.main.dtos.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse> sqlUnexpectedException(SQLException sqlException) {
        String message = "Internal error due to sql conflict";
        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST,
                LocalDateTime.now(), message, sqlException.getClass().toString());

        log.error(sqlException.getMessage(), sqlException.getCause());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> exception(Exception exception) {
        String message = "Something unexpected happened.";
        ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST,
                LocalDateTime.now(), message, exception.getClass().toString());

        log.error(exception.getMessage(), exception.getCause());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
