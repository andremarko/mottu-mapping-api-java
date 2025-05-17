package com.mottu.mapping.api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class MotoResponseDTO {
    private Long motorcycleId;
    private String plate;
    private String chassis;
    private ModelResponseDTO model;
    private SectorResponseDTO sector;
    private LocalDateTime entry;
    private String description;
}