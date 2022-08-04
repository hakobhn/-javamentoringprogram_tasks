package com.epam.mentoring.messenger.exception;

public class InvalidDataPairException extends RuntimeException {

    public InvalidDataPairException(String message) {
        super(message);
    }

    public InvalidDataPairException(String message, Throwable t) {
        super(message, t);
    }

}
