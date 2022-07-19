package com.epam.mentoring.messenger.messenger.exception;

public class InvalidAppParamsProvidedException extends RuntimeException {

    public InvalidAppParamsProvidedException(String message) {
        super(message);
    }

    public InvalidAppParamsProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

}
