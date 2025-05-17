package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class SectorResponseDTO {
    private Long sectorId;
    private MotoYardResponseDTO yard;
    private SectorTypeResponseDTO type;
}
