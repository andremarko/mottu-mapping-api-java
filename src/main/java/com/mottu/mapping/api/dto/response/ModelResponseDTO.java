package com.mottu.mapping.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ModelResponseDTO {
    private Long modelId;
    private String modelName;
}
