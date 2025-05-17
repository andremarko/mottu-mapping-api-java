package com.mottu.mapping.api.dto.response;

import lombok.Data;

@Data
public class UnitResponseDTO {
    private Long unit_id;
    private String name;
    private String address;
    private String number;
    private String district;
    private String zipCode;
    private String city;
    private String state;
}