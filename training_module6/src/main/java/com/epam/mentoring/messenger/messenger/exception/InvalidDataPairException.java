package com.epam.mentoring.messenger.messenger.exception;

public class InvalidDataPairException extends RuntimeException {

    public InvalidDataPairException(String message) {
        super(message);
    }

    public InvalidDataPairException(String message, Throwable cause) {
        super(message, cause);
    }

}