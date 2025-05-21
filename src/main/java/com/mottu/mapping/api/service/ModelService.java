package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.ModelRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.exception.ModelNotFoundException;
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

    public ModelResponseDTO save(ModelRequestDTO dto) {
        Model model = new Model(dto.getModelName());
        Model saved = modelRepository.save(model);
        return toResponseDTO(saved);
    }

    public List<ModelResponseDTO> readAll() {
        List<Model> models = modelRepository.findAll();
        return models.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ModelResponseDTO read(Long modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ModelNotFoundException(modelId));
        return toResponseDTO(model);
    }

    public ModelResponseDTO update(Long modelId, ModelRequestDTO dto) {
        Model existingModel = modelRepository.findById(modelId)
                .orElseThrow(() -> new ModelNotFoundException(modelId));
        existingModel.setModelName(dto.getModelName());
        Model updated = modelRepository.save(existingModel);
        return toResponseDTO(updated);
    }

    public void delete(Long modelId) {
        if (!modelRepository.existsById(modelId)) {
            throw new ModelNotFoundException(modelId);
        }
        modelRepository.deleteById(modelId);
    }

    private ModelResponseDTO toResponseDTO(Model model) {
        return new ModelResponseDTO(model.getModelId(), model.getModelName());
    }
}
