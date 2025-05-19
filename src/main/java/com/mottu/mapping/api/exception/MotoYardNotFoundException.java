package com.mottu.mapping.api.exception;

public class MotoYardNotFoundException extends RuntimeException {
    public MotoYardNotFoundException(Long id) {
        super("MotoYard not found with id: " + id);
    }
}
