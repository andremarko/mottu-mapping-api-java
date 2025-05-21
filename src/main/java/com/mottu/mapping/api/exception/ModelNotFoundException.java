package com.mottu.mapping.api.exception;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Long id) {
        super("Model not found with id: " + id);
    }
}
