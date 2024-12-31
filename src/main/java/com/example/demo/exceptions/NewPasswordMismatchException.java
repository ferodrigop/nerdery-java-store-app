package com.example.demo.exceptions;

public class NewPasswordMismatchException extends RuntimeException {
    public NewPasswordMismatchException() {
        super();
    }

    public NewPasswordMismatchException(String message) {
        super(message);
    }
}
