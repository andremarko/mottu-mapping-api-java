package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.exception.ModelNotFoundException;
import com.mottu.mapping.api.exception.SectorNotFoundException;
import com.mottu.mapping.api.exception.MotoNotFoundException;
import com.mottu.mapping.api.model.Model;
import com.mottu.mapping.api.model.Moto;
import com.mottu.mapping.api.model.MotoYard;
import com.mottu.mapping.api.model.Sector;
import com.mottu.mapping.api.repository.ModelRepository;
import com.mottu.mapping.api.repository.MotoRepository;
import com.mottu.mapping.api.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private SectorRepository sectorRepository;

    public MotoService(MotoRepository motoRepository, ModelRepository modelRepository, SectorRepository sectorRepository) {
        this.motoRepository = motoRepository;
        this.modelRepository = modelRepository;
        this.sectorRepository = sectorRepository;
    }

    public MotoResponseDTO save(MotoRequestDTO dto) {
        Model model = modelRepository.findById(dto.getModelId())
                .orElseThrow(() -> new ModelNotFoundException(dto.getModelId()));

        Sector sector = sectorRepository.findById(dto.getSectorId())
                .orElseThrow(() -> new SectorNotFoundException(dto.getSectorId()));

        Moto moto = new Moto(dto.getPlate(), dto.getCoordinates(), model, sector);
        Moto saved = motoRepository.save(moto);
        return toResponseDTO(saved);
    }

    public Page<MotoResponseDTO> readAll(Pageable pageable) {
        Page<Moto> page = motoRepository.findAll(pageable);
        if (page.isEmpty()) {
            throw new MotoNotFoundException(null);
        }
        return page.map(this::toResponseDTO);
    }

    public MotoResponseDTO read(Long motoId) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new MotoNotFoundException(motoId));
        return toResponseDTO(moto);
    }

    public MotoResponseDTO update(Long motoId, MotoRequestDTO dto) {
        Moto existingMoto = motoRepository.findById(motoId)
                .orElseThrow(() -> new MotoNotFoundException(motoId));

        Model model = modelRepository.findById(dto.getModelId())
                .orElseThrow(() -> new ModelNotFoundException(dto.getModelId()));

        Sector sector = sectorRepository.findById(dto.getSectorId())
                .orElseThrow(() -> new SectorNotFoundException(dto.getSectorId()));

        existingMoto.setPlate(dto.getPlate());
        existingMoto.setCoordinates(dto.getCoordinates());
        existingMoto.setModel(model);
        existingMoto.setSector(sector);

        Moto updated = motoRepository.save(existingMoto);
        return toResponseDTO(updated);
    }

    public void delete(Long motoId) {
        if (!motoRepository.existsById(motoId)) {
            throw new MotoNotFoundException(motoId);
        }
        motoRepository.deleteById(motoId);
    }

    private MotoResponseDTO toResponseDTO(Moto moto) {
        Sector sector = moto.getSector();

        return new MotoResponseDTO(
                moto.getMotorcycleId(),
                moto.getPlate(),
                moto.getCoordinates(),
                new ModelResponseDTO(moto.getModel().getModelId(), moto.getModel().getModelName()),
                new SectorResponseDTO(
                        sector.getSectorId(),
                        toMotoYardResponseDTO(sector.getYard()),
                        sector.getName(),
                        sector.getDescription(),
                        sector.getColorRgb(),
                        sector.getColorName()
                )
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
