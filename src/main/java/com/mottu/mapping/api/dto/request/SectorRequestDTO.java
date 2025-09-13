package com.mottu.mapping.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SectorRequestDTO {
    @NotNull(message = "Yard Id is required")
    private Long yardId;
    @NotBlank(message = "Sector name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "RGB color is required")
    private String colorRgb;
    @NotBlank(message = "Color name is required")
    private String colorName;
}
