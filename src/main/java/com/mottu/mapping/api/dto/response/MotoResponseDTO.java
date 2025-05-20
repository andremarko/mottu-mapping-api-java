package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class MotoResponseDTO {
 private Long motorcycleId;
 private String plate;
 private String coordinates;
 private ModelResponseDTO model;
 private SectorResponseDTO sector;

 public MotoResponseDTO() {}

 public MotoResponseDTO(Long motorcycleId, String plate, String coordinates, ModelResponseDTO model, SectorResponseDTO sector) {
  this.motorcycleId = motorcycleId;
  this.plate = plate;
  this.coordinates = coordinates;
  this.model = model;
  this.sector = sector;
 }
}