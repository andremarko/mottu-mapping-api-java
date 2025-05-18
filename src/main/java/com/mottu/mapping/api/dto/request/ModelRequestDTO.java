package com.mottu.mapping.api.dto.request;

import jakarta.validation.constraints.NotBlank;;
import lombok.Data;

@Data
public class ModelRequestDTO {
    @NotBlank(message="Model name is required")
    private String modelName;

    public ModelRequestDTO() {}

    public ModelRequestDTO(String modelName) {
        this.modelName = modelName;
    }
}
