package com.mottu.mapping.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MotoYardRequestDTO {
    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 256, message = "Description must be between 2 and 256 characters")
    private String description;
    @NotNull(message = "Capacity is required")
    private Integer capacity;

    public MotoYardRequestDTO() {}

    public MotoYardRequestDTO(String description, Integer capacity) {
        this.description = description;
        this.capacity = capacity;
    }
}
