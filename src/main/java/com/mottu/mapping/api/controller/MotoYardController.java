package com.mottu.mapping.api.controller;

import com.mottu.mapping.api.dto.request.MotoYardRequestDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.service.MotoYardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motoyards")
public class MotoYardController {

    @Autowired
    private MotoYardService motoYardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MotoYardResponseDTO create(@Valid @RequestBody MotoYardRequestDTO dto) {
        return motoYardService.save(dto);
    }

    @GetMapping
    public List<MotoYardResponseDTO> getAll() {
        return motoYardService.readAll();
    }

    @GetMapping("/{id}")
    public MotoYardResponseDTO getById(@PathVariable Long id) {
        return motoYardService.read(id);
    }

    @PutMapping("/{id}")
    public MotoYardResponseDTO update(@PathVariable Long id, @Valid @RequestBody MotoYardRequestDTO dto) {
        return motoYardService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        motoYardService.delete(id);
    }
}
