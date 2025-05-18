package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class ModelResponseDTO {
    private Long modelId;
    private String modelName;

    public ModelResponseDTO() {}

    public ModelResponseDTO(Long modelId, String modelName) {
        this.modelId = modelId;
        this.modelName = modelName;
    }
}
