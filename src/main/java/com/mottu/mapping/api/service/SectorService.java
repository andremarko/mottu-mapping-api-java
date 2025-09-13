package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.SectorRequestDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.exception.MotoYardNotFoundException;
import com.mottu.mapping.api.exception.SectorNotFoundException;
import com.mottu.mapping.api.mapper.SectorMapper;
import com.mottu.mapping.api.model.MotoYard;
import com.mottu.mapping.api.model.Sector;
import com.mottu.mapping.api.repository.MotoYardRepository;
import com.mottu.mapping.api.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private MotoYardRepository motoYardRepository;

    @Autowired
    private SectorMapper sectorMapper;
    // retorna response DTO, recebe como param corpo de request (post)
    public SectorResponseDTO save(SectorRequestDTO dto) {
        MotoYard yard = motoYardRepository.findById(dto.getYardId())
                .orElseThrow(() -> new MotoYardNotFoundException(dto.getYardId()));

        Sector sector = sectorMapper.toEntity(dto);
        sector.setYard(yard);
        Sector saved = sectorRepository.save(sector);
        return sectorMapper.toResponseDTO(saved);
    }

    // paginado, retorna uma tupla
    public Page<SectorResponseDTO> getAll(Pageable pageable) {
        return sectorRepository.findAll(pageable)
                .map(sectorMapper::toResponseDTO);
    }

    // retorna response DTO, recebe como param o id (get)
    public SectorResponseDTO getById(Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new SectorNotFoundException(sectorId));
        return sectorMapper.toResponseDTO(sector);
    }

    public SectorResponseDTO update(Long sectorId, SectorRequestDTO dto) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new SectorNotFoundException(sectorId));

        MotoYard yard = motoYardRepository.findById(dto.getYardId())
                .orElseThrow(() -> new MotoYardNotFoundException(dto.getYardId()));

       sectorMapper.updateEntityFromDTO(dto, sector);

       sector.setYard(yard);

       Sector updated = sectorRepository.save(sector);

       return sectorMapper.toResponseDTO(updated);
    }

    public void delete(Long sectorId) {
        if (!sectorRepository.existsById(sectorId)) {
            throw new SectorNotFoundException(sectorId);
        }
        sectorRepository.deleteById(sectorId);
    }
}
