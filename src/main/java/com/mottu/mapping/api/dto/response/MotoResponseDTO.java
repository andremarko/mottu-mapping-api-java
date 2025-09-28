package com.mottu.mapping.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MotoResponseDTO {
 private Long motorcycleId;
 private String plate;
 private String coordinates;
 private ModelResponseDTO model;
 private SectorResponseDTO sector;
}