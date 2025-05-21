package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.MotoYardRequestDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.exception.MotoYardNotFoundException;
import com.mottu.mapping.api.model.MotoYard;
import com.mottu.mapping.api.repository.MotoYardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoYardService {

    @Autowired
    private MotoYardRepository motoYardRepository;

    public MotoYardResponseDTO save(MotoYardRequestDTO dto) {
        MotoYard motoYard = new MotoYard();
        motoYard.setDescription(dto.getDescription());
        motoYard.setCapacity(dto.getCapacity());
        MotoYard saved = motoYardRepository.save(motoYard);
        return toResponseDTO(saved);
    }

    public List<MotoYardResponseDTO> readAll() {
        List<MotoYard> yards = motoYardRepository.findAll();
        return yards.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MotoYardResponseDTO read(Long yardId) {
        MotoYard motoYard = motoYardRepository.findById(yardId)
                .orElseThrow(() -> new MotoYardNotFoundException(yardId));
        return toResponseDTO(motoYard);
    }

    public MotoYardResponseDTO update(Long yardId, MotoYardRequestDTO dto) {
        MotoYard existing = motoYardRepository.findById(yardId)
                .orElseThrow(() -> new MotoYardNotFoundException(yardId));

        existing.setDescription(dto.getDescription());
        existing.setCapacity(dto.getCapacity());

        MotoYard updated = motoYardRepository.save(existing);
        return toResponseDTO(updated);
    }

    public void delete(Long yardId) {
        if (!motoYardRepository.existsById(yardId)) {
            throw new MotoYardNotFoundException(yardId);
        }
        motoYardRepository.deleteById(yardId);
    }

    private MotoYardResponseDTO toResponseDTO(MotoYard motoYard) {
        return new MotoYardResponseDTO(
                motoYard.getYardId(),
                motoYard.getDescription(),
                motoYard.getCapacity()
        );
    }
}
