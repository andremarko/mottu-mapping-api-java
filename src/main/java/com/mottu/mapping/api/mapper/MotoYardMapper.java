package com.mottu.mapping.api.mapper;

import com.mottu.mapping.api.dto.request.MotoYardRequestDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.model.MotoYard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MotoYardMapper {
    MotoYardResponseDTO toResponseDTO(MotoYard motoYard);
    MotoYard toEntity(MotoYardRequestDTO dto);
    void updateEntityFromDTO(MotoYardRequestDTO dto, @MappingTarget MotoYard motoYard);
}
