package com.clinic.main.customeExceptions;

public class UserNotFoundException extends RuntimeException {

    private final String message;
    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
