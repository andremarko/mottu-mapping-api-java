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

import java.util.List;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private MotoYardRepository motoYardRepository;

    @Autowired
    private SectorMapper sectorMapper;

    public SectorResponseDTO save(SectorRequestDTO dto) {
        MotoYard yard = motoYardRepository.findById(dto.getYardId())
                .orElseThrow(() -> new MotoYardNotFoundException(dto.getYardId()));

        Sector sector = sectorMapper.toEntity(dto);
        sector.setYard(yard);
        Sector saved = sectorRepository.save(sector);
        return sectorMapper.toResponseDTO(saved);
    }

    public Page<SectorResponseDTO> getAll(Pageable pageable) {
        return sectorRepository.findAll(pageable)
                .map(sectorMapper::toResponseDTO);
    }

    public SectorResponseDTO getById(Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new SectorNotFoundException(sectorId));
        return sectorMapper.toResponseDTO(sector);
    }

    public List<SectorResponseDTO> getByYardId(Long yardId) {
        List<Sector> sectors = sectorRepository.findByYard_YardId(yardId);
        if (sectors.isEmpty()) {
            throw new SectorNotFoundException(yardId);
        }
        return sectorMapper.toResponseDTOList(sectors);
    }

    public SectorResponseDTO update(Long sectorId, SectorRequestDTO dto) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new SectorNotFoundException(sectorId));

        MotoYard yard = motoYardRepository.findById(dto.getYardId())
                .orElseThrow(() -> new MotoYardNotFoundException(dto.getYardId()));

        sectorMapper.updateEntityFromDTO(dto, sector);


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
