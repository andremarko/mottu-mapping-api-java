package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class MotoYardResponseDTO {
    private Long yardId;
    private String description;
    private Integer capacity;

    public MotoYardResponseDTO() {}

    public MotoYardResponseDTO(Long yardId, String description, Integer capacity) {
        this.yardId = yardId;
        this.description = description;
        this.capacity = capacity;
    }
}
