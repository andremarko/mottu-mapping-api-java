package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.exception.ModelNotFoundException;
import com.mottu.mapping.api.exception.SectorNotFoundException;
import com.mottu.mapping.api.model.Model;
import com.mottu.mapping.api.model.Moto;
import com.mottu.mapping.api.model.Sector;
import com.mottu.mapping.api.repository.ModelRepository;
import com.mottu.mapping.api.repository.MotoRepository;
import com.mottu.mapping.api.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    public Moto save(MotoRequestDTO dto) {
        Model model = modelRepository.findById(dto.getModelId())
                .orElseThrow(() -> new ModelNotFoundException(dto.getModelId()));

        Sector sector = sectorRepository.findById(dto.getSectorId())
                .orElseThrow(() -> new SectorNotFoundException(dto.getSectorId()));

        Moto moto = new Moto(dto.getPlate(), dto.getCoordinates(), model, sector);
        return motoRepository.save(moto);
    }

    public List<Moto> readAll() {
        List<Moto> motos = motoRepository.findAll();
        if (motos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No motos found");
        }
        return motos;
    }

    public Moto read(Long motoId) {
        return motoRepository.findById(motoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto not found with id: " + motoId));
    }

    public Moto update(Moto moto) {
        Moto existingMoto = motoRepository.findById(moto.getMotorcycleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto not found with id: " + moto.getMotorcycleId()));

        existingMoto.setPlate(moto.getPlate());
        existingMoto.setCoordinates(moto.getCoordinates());
        existingMoto.setModel(moto.getModel());
        existingMoto.setSector(moto.getSector());

        return motoRepository.save(existingMoto);
    }

    public void delete(Long motoId) {
        if (!motoRepository.existsById(motoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto not found with id: " + motoId);
        }
        motoRepository.deleteById(motoId);
    }
}
