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

public class MotoYardRequestDTO {
    @NotBlank(message = "Branch name is required")
    @Size(min = 2, max = 100, message = "Branch name must be between 2 and 100 characters")
    private String branchName;
    @NotBlank(message = "Address is required")
    @Size(min = 2, max = 256, message = "Address must be between 2 and 256 characters")
    private String address;
    @NotBlank(message = "City is required")
    @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
    private String city;
    @NotBlank(message = "State is required")
    @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
    private String state;
    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 256, message = "Description must be between 2 and 256 characters")
    private String description;
    @NotNull(message = "Capacity is required")
    private Integer capacity;
}
