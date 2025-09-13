package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.MotoYardRequestDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.exception.MotoYardNotFoundException;
import com.mottu.mapping.api.mapper.MotoYardMapper;
import com.mottu.mapping.api.model.MotoYard;
import com.mottu.mapping.api.repository.MotoYardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MotoYardService {

    @Autowired
    private MotoYardRepository motoYardRepository;

    @Autowired
    private MotoYardMapper motoYardMapper;

    public MotoYardResponseDTO save(MotoYardRequestDTO dto) {
        MotoYard motoYard = motoYardMapper.toEntity(dto);
        MotoYard saved = motoYardRepository.save(motoYard);
        return motoYardMapper.toResponseDTO(saved);
    }

    // paginado
    public Page<MotoYardResponseDTO> getAll(Pageable pageable) {
        return motoYardRepository.findAll(pageable)
                .map(motoYardMapper::toResponseDTO);
    }

    public MotoYardResponseDTO getById(Long yardId) {
        MotoYard motoYard = motoYardRepository.findById(yardId)
                .orElseThrow(() -> new MotoYardNotFoundException(yardId));
        return motoYardMapper.toResponseDTO(motoYard);
    }

    public MotoYardResponseDTO update(Long yardId, MotoYardRequestDTO dto) {
        MotoYard motoYard = motoYardRepository.findById(yardId)
                .orElseThrow(() -> new MotoYardNotFoundException(yardId));

        motoYardMapper.updateEntityFromDTO(dto, motoYard);

        MotoYard updated = motoYardRepository.save(motoYard);

        return motoYardMapper.toResponseDTO(updated);
    }

    public void delete(Long yardId) {
        if (!motoYardRepository.existsById(yardId)) {
            throw new MotoYardNotFoundException(yardId);
        }
        motoYardRepository.deleteById(yardId);
    }
}
