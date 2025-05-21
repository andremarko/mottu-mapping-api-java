package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class SectorResponseDTO {
    private Long sectorId;
    private MotoYardResponseDTO yardId;
    private String name;
    private String description;
    private String colorRgb;
    private String colorName;

    public SectorResponseDTO() {}

    public SectorResponseDTO(Long sectorId, MotoYardResponseDTO yardId, String name, String description, String colorRgb, String colorName) {
        this.sectorId = sectorId;
        this.yardId = yardId;
        this.name = name;
        this.description = description;
        this.colorRgb = colorRgb;
        this.colorName = colorName;
    }
}
