package com.example.demo.exceptions;

public class CurrentPasswordException extends RuntimeException {
    public CurrentPasswordException() {
        super();
    }

    public CurrentPasswordException(String message) {
        super(message);
    }
}
