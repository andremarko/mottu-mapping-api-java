package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class MotoYardResponseDTO {
    private Long yardId;
    private String description;
    private Integer capacity;
    private UnitResponseDTO unit;
}
