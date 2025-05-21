package com.mottu.mapping.api.exception;

public class SectorNotFoundException extends RuntimeException {
    public SectorNotFoundException(Long id) {
        super("Sector not found with id " + id);
    }
}
