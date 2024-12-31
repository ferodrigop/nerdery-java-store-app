package com.example.demo.exceptions;

public class EntityRelationshipMismatchException extends RuntimeException {
    public EntityRelationshipMismatchException() {
        super();
    }

    public EntityRelationshipMismatchException(String message) {
        super(message);
    }
}
