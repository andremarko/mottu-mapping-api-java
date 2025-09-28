package com.mottu.mapping.api.mapper;

import com.mottu.mapping.api.dto.request.SectorRequestDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.model.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {
    SectorResponseDTO toResponseDTO(Sector sector);
    List<SectorResponseDTO> toResponseDTOList(List<Sector> sectors);
    @Mapping(target = "yard", ignore = true)
    Sector toEntity(SectorRequestDTO dto);
    void updateEntityFromDTO(SectorRequestDTO dto, @MappingTarget Sector sector);
}
