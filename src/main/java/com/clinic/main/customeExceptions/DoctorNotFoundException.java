package com.clinic.main.customeExceptions;

public class DoctorNotFoundException extends RuntimeException {

    private final String message;

    public DoctorNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
