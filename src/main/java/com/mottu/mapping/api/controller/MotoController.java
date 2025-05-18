package com.mottu.mapping.api.controller;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.exception.ModelNotFoundException;
import com.mottu.mapping.api.exception.SectorNotFoundException;
import com.mottu.mapping.api.model.Moto;
import com.mottu.mapping.api.repository.ModelRepository;
import com.mottu.mapping.api.repository.MotoRepository;
import com.mottu.mapping.api.repository.SectorRepository;
import com.mottu.mapping.api.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/motos")
public class MotoController {
    @Autowired
    private MotoService motoService;

    @PostMapping
    public ResponseEntity<MotoResponseDTO> create(@Valid @RequestBody MotoRequestDTO dto) {
        Moto moto = motoService.save(dto);
        return ResponseEntity.ok(toMotoResponseDTO(moto));
    }




    private MotoResponseDTO toMotoResponseDTO(Moto moto) {
        ModelResponseDTO modelDTO = new ModelResponseDTO(
                moto.getModel().getModelId(),
                moto.getModel().getModelName()
        );

        MotoYardResponseDTO yardDTO = new MotoYardResponseDTO(
                moto.getSector().getYard().getYardId(),
                moto.getSector().getYard().getDescription(),
                moto.getSector().get
        );

        SectorResponseDTO sectorDTO = new SectorResponseDTO(
                moto.getSector().getId(),
                yardDTO,
                moto.getSector().getName(),
                moto.getSector().getDescription(),
                moto.getSector().getColorRgb(),
                moto.getSector().getColorName()
        );

        return new MotoResponseDTO(
                moto.getId(),
                moto.getPlate(),
                moto.getCoordinates(),
                modelDTO,
                sectorDTO
        );
    }
}