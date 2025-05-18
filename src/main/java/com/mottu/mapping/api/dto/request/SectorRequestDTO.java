package com.mottu.mapping.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
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

    public SectorRequestDTO() {}

    public SectorRequestDTO(Long yardId, String name, String description, String colorRgb, String colorName) {
        this.yardId = yardId;
        this.name = name;
        this.description = description;
        this.colorRgb = colorRgb;
        this.colorName = colorName;
    }
}
