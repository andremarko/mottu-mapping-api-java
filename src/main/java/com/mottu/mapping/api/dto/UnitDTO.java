package com.mottu.mapping.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnitDTO {
    private Long unit_id;
    private String name;
    private String address;
    private String number;
    private String district;
    private String zipCode;
    private String city;
    private String state;
}