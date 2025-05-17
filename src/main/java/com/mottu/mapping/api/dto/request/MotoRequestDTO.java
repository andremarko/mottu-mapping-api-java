package com.mottu.mapping.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotoRequestDTO {

    @NotBlank(message="License plate is required.")
    @Size(max = 8, message = "License plate cannot be longer than 8 characters.")
    @Pattern(
            regexp = "^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}$",
            message = "Invalid license plate."
    )
    private String plate;

    @NotBlank(message="Chassis is required.")
    @Size(max = 17, message = "Chassis must be 17 characters.")
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$", message = "Invalid chassis number.")
    private String chassis;

    @NotNull(message="Entry is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime entry;

    @Size(max=256, message="Description can't have more than 256 characters")
    private String description;

    @NotNull
    private Long modelId;

    @NotNull
    private Long sectorId;
}
