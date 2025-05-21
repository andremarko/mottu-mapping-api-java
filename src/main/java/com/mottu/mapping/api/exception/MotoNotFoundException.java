package com.mottu.mapping.api.exception;

public class MotoNotFoundException extends RuntimeException {
    public MotoNotFoundException(Long id) {
        super("Moto not found with id: " + id);
    }
}
