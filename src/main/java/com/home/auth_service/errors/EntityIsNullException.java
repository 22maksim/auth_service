package com.home.auth_service.errors;

public class EntityIsNullException extends RuntimeException {
    public EntityIsNullException(String message) {
        super(message);
    }
}
