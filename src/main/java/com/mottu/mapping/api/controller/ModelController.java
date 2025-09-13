package com.mottu.mapping.api.controller;

import com.mottu.mapping.api.dto.request.ModelRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.service.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModelResponseDTO create(@Valid @RequestBody ModelRequestDTO dto) {
        return modelService.save(dto);
    }

    @GetMapping
    public List<ModelResponseDTO> getAll() {
        return modelService.getAll();
    }

    @GetMapping("/{id}")
    public ModelResponseDTO getById(@PathVariable Long id) {
        return modelService.getById(id);
    }

    @PutMapping("/{id}")
    public ModelResponseDTO update(@PathVariable Long id, @Valid @RequestBody ModelRequestDTO dto) {
        return modelService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        modelService.delete(id);
    }
}
