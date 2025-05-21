package com.mottu.mapping.api.controller;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MotoResponseDTO create(@Valid @RequestBody MotoRequestDTO dto) {
        return motoService.save(dto);
    }

    @GetMapping
    public Page<MotoResponseDTO> getAll(@RequestParam(required = false) String plate,
                                        @RequestParam(required = false) Long sectorId,
                                        Pageable pageable) {
        return motoService.readAllFiltered(plate, sectorId, pageable);
    }

    @GetMapping("/{id}")
    public MotoResponseDTO getById(@PathVariable Long id) {
        return motoService.read(id);
    }

    @PutMapping("/{id}")
    public MotoResponseDTO update(@PathVariable Long id,@Valid @RequestBody MotoRequestDTO dto) {
        return motoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        motoService.delete(id);
    }
}