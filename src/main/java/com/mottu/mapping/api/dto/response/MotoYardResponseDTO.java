package com.mottu.mapping.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MotoYardResponseDTO {
    private Long yardId;
    private String branchName;
    private String address;
    private String city;
    private String state;
    private String description;
    private Integer capacity;
}
