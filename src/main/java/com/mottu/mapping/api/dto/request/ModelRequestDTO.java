package com.mottu.mapping.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ModelRequestDTO {
    @NotBlank(message="Model name is required")
    private String modelName;
}
