package com.mottu.mapping.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelDTO {
    @NotNull
    private Long model_id;
    @NotNull
    private String model_name;
}
