package com.mottu.mapping.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MotoRequestDTO {
    @NotBlank(message="License plate is required.")
    @Size(max = 8, message = "License plate cannot be longer than 8 characters.")
    private String plate;
    @NotBlank(message="Coordinates cannot be null")
    private String coordinates;
    @NotNull(message = "Model ID is required")
    private Long modelId;
    @NotNull(message = "Sector ID is required")
    private Long sectorId;
}
