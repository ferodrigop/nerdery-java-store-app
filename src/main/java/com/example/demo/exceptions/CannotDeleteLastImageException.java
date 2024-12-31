package com.example.demo.exceptions;

public class CannotDeleteLastImageException extends RuntimeException {
    public CannotDeleteLastImageException() {
        super();
    }

    public CannotDeleteLastImageException(String message) {
        super(message);
    }
}
