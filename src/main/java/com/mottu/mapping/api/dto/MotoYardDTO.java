package com.mottu.mapping.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MotoYardDTO {

    private Long yardId;
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotNull(message = "Capacity cannot be null")
    private Integer capacity;
    @NotNull(message = "Unit cannot be null")
    private UnitDTO unit;
}
