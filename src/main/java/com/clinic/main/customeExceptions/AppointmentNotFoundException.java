package com.clinic.main.customeExceptions;

public class AppointmentNotFoundException extends RuntimeException {

    private final String message;

    public AppointmentNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
