package com.epam.mentoring.messenger.messenger.exception;

public class InvalidDataProvidedException extends RuntimeException {

    public InvalidDataProvidedException(String message) {
        super(message);
    }

    public InvalidDataProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

}
