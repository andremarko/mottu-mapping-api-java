package com.mottu.mapping.api.mapper;

import com.mottu.mapping.api.dto.request.ModelRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.model.Model;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    ModelResponseDTO toResponseDTO(Model model);
    Model toEntity(ModelRequestDTO dto);
    void updateEntityFromDTO(ModelRequestDTO dto, @MappingTarget Model model);
}
