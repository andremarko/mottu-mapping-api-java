package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.SectorRequestDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.exception.MotoYardNotFoundException;
import com.mottu.mapping.api.exception.SectorNotFoundException;
import com.mottu.mapping.api.model.MotoYard;
import com.mottu.mapping.api.model.Sector;
import com.mottu.mapping.api.repository.MotoYardRepository;
import com.mottu.mapping.api.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private MotoYardRepository motoYardRepository;

    public SectorResponseDTO save(SectorRequestDTO dto) {
        MotoYard yard = motoYardRepository.findById(dto.getYardId())
                .orElseThrow(() -> new MotoYardNotFoundException(dto.getYardId()));

        Sector sector = new Sector();
        sector.setYard(yard);
        sector.setName(dto.getName());
        sector.setDescription(dto.getDescription());
        sector.setColorRgb(dto.getColorRgb());
        sector.setColorName(dto.getColorName());
        sector.setMotos(null); // inicialmente sem motos

        Sector saved = sectorRepository.save(sector);
        return toResponseDTO(saved);
    }

    public List<SectorResponseDTO> readAll() {
        List<Sector> sectors = sectorRepository.findAll();
        if (sectors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No sectors found");
        }
        return sectors.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SectorResponseDTO read(Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new SectorNotFoundException(sectorId));
        return toResponseDTO(sector);
    }

    public SectorResponseDTO update(Long sectorId, SectorRequestDTO dto) {
        Sector existingSector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new SectorNotFoundException(sectorId));

        MotoYard yard = motoYardRepository.findById(dto.getYardId())
                .orElseThrow(() -> new MotoYardNotFoundException(dto.getYardId()));

        existingSector.setYard(yard);
        existingSector.setName(dto.getName());
        existingSector.setDescription(dto.getDescription());
        existingSector.setColorRgb(dto.getColorRgb());
        existingSector.setColorName(dto.getColorName());

        Sector updated = sectorRepository.save(existingSector);
        return toResponseDTO(updated);
    }

    public void delete(Long sectorId) {
        if (!sectorRepository.existsById(sectorId)) {
            throw new SectorNotFoundException(sectorId);
        }
        sectorRepository.deleteById(sectorId);
    }

    private SectorResponseDTO toResponseDTO(Sector sector) {
        return new SectorResponseDTO(
                sector.getSectorId(),
                toMotoYardResponseDTO(sector.getYard()),
                sector.getName(),
                sector.getDescription(),
                sector.getColorRgb(),
                sector.getColorName()
        );
    }

    private MotoYardResponseDTO toMotoYardResponseDTO(MotoYard yard) {
        if (yard == null) return null;
        return new MotoYardResponseDTO(
                yard.getYardId(),
                yard.getDescription(),
                yard.getCapacity()
        );
    }
}
