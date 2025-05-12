package com.mottu.mapping.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class MotoResponseDTO {
    private Long motorcycle_id;
    private String plate;
    private String chassis;
    private ModelDTO model;
    private SectorDTO sector;
    private LocalDateTime entry;
    private String description;
}