package com.project.master.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User was not found in database.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
