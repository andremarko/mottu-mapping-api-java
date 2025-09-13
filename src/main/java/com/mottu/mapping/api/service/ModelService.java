package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.ModelRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.exception.ModelNotFoundException;
import com.mottu.mapping.api.mapper.ModelMapper;
import com.mottu.mapping.api.model.Model;
import com.mottu.mapping.api.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ModelResponseDTO save(ModelRequestDTO dto) {
        Model model = modelMapper.toEntity(dto);
        Model saved = modelRepository.save(model);
        return modelMapper.toResponseDTO(saved);
    }

    public List<ModelResponseDTO> getAll() {
        return modelRepository.findAll().
                stream()
                .map(modelMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ModelResponseDTO getById(Long modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ModelNotFoundException(modelId));
        return modelMapper.toResponseDTO(model);
    }

    public ModelResponseDTO update(Long modelId, ModelRequestDTO dto) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ModelNotFoundException(modelId));

        modelMapper.updateEntityFromDTO(dto, model);

        Model updated = modelRepository.save(model);

        return modelMapper.toResponseDTO(updated);
    }

    public void delete(Long modelId) {
        if (!modelRepository.existsById(modelId)) {
            throw new ModelNotFoundException(modelId);
        }
        modelRepository.deleteById(modelId);
    }
}
