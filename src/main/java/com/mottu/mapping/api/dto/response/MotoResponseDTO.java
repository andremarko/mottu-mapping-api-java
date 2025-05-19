package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class MotoResponseDTO {
 private Long motoId;
 private String plate;
 private String coordinates;
 private ModelResponseDTO model;
 private SectorResponseDTO sector;

 public MotoResponseDTO() {}

 public MotoResponseDTO(Long motoId, String plate, String coordinates, ModelResponseDTO model, SectorResponseDTO sector) {
  this.motoId = motoId;
  this.plate = plate;
  this.coordinates = coordinates;
  this.model = model;
  this.sector = sector;
 }
}