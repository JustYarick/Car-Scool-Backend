package com.kubgtu.car_school.exception.ExceptionClass;

public class NotValidatedException extends RuntimeException {
    public NotValidatedException(String message) {
        super(message);
    }

  public NotValidatedException(String message, Throwable cause) {
    super(message, cause);
  }
}
