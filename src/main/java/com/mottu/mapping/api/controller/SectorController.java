package com.mottu.mapping.api.controller;

import com.mottu.mapping.api.dto.request.SectorRequestDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.service.SectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SectorResponseDTO create(@Valid @RequestBody SectorRequestDTO dto) {
        return sectorService.save(dto);
    }

    @GetMapping
    public List<SectorResponseDTO> getAll() {
        return sectorService.readAll();
    }

    @GetMapping("/{id}")
    public SectorResponseDTO getById(@PathVariable Long id) {
        return sectorService.read(id);
    }

    @PutMapping("/{id}")
    public SectorResponseDTO update(@PathVariable Long id, @Valid @RequestBody SectorRequestDTO dto) {
        return sectorService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sectorService.delete(id);
    }
}
