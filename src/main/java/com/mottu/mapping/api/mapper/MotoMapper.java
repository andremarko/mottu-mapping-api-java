package com.mottu.mapping.api.mapper;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.model.Model;
import com.mottu.mapping.api.model.Moto;
import com.mottu.mapping.api.model.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ModelMapper.class, SectorMapper.class})
public interface MotoMapper {
    MotoResponseDTO toResponseDTO(Moto moto);
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "sector", ignore = true)
    Moto toEntity(MotoRequestDTO dto, Model model, Sector sector);
    void updateEntityFromDTO(MotoRequestDTO dto, @MappingTarget Moto moto);
}
