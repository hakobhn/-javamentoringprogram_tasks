package com.epam.multithreading.training.task5.exception;

public class InvalidDataSubmittedException extends RuntimeException {
    public InvalidDataSubmittedException(String message) {
        super(message);
    }

    public InvalidDataSubmittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
