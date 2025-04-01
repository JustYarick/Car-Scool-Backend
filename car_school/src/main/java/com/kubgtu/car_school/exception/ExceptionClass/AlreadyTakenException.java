package com.kubgtu.car_school.exception.ExceptionClass;

public class AlreadyTakenException extends RuntimeException {
    public AlreadyTakenException(String message) {
        super(message);
    }

    public AlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
