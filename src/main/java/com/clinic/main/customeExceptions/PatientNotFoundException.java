package com.clinic.main.customeExceptions;

public class PatientNotFoundException extends RuntimeException{

    private final String message;
    public PatientNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
