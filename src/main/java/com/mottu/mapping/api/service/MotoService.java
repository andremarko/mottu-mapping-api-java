package com.mottu.mapping.api.service;

import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.exception.ModelNotFoundException;
import com.mottu.mapping.api.exception.SectorNotFoundException;
import com.mottu.mapping.api.exception.MotoNotFoundException;
import com.mottu.mapping.api.mapper.MotoMapper;
import com.mottu.mapping.api.model.Model;
import com.mottu.mapping.api.model.Moto;
import com.mottu.mapping.api.model.Sector;
import com.mottu.mapping.api.repository.ModelRepository;
import com.mottu.mapping.api.repository.MotoRepository;
import com.mottu.mapping.api.repository.SectorRepository;
import com.mottu.mapping.api.util.EntityPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"motos", "motosAll"})
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private MotoMapper motoMapper;

    // helper, para evitar bloat de código. Retorna uma tupla com Model e Sector
    private EntityPair<Model, Sector, Object> findModelAndSectorById(Long modelId, Long sectorId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ModelNotFoundException(modelId));

        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new SectorNotFoundException(sectorId));

        return new EntityPair<>(model, sector, Optional.empty());
    }

    // Cache pelo ID da moto
    @CachePut(value = "motos", key = "#result.motorcycleId")
    @CacheEvict(value= "motosAll", allEntries = true)
    public MotoResponseDTO save(MotoRequestDTO dto) {
        EntityPair<Model, Sector, Object> pair = findModelAndSectorById(dto.getModelId(), dto.getSectorId());

        Moto moto = motoMapper.toEntity(dto, pair.first(), pair.second());

        moto.setModel(pair.first());
        moto.setSector(pair.second());

        Moto saved = motoRepository.save(moto);
        return motoMapper.toResponseDTO(saved);
    }

    @Cacheable(value = "motosAll", key = "'page:' + #pageable.pageNumber + ':size:' + #pageable.pageSize")
    public Page<MotoResponseDTO> getAll(Pageable pageable) {
        return motoRepository.findAll(pageable)
                .map(motoMapper::toResponseDTO);
    }

    @Cacheable(value = "motos", key = "#motoId")
    public MotoResponseDTO getById(Long motoId) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new MotoNotFoundException(motoId));
        return motoMapper.toResponseDTO(moto);
    }

    public Page<MotoResponseDTO> getBySector_YardId(Long yardId, Pageable pageable) {
        return motoRepository.findBySector_Yard_YardId(yardId, pageable)
                .map(motoMapper::toResponseDTO);
    }

    @CachePut(value = "motos", key = "#result.motorcycleId")
    @CacheEvict(value = "motosAll", allEntries = true)
    public MotoResponseDTO update(Long motoId, MotoRequestDTO dto) {

        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new MotoNotFoundException(motoId));

        EntityPair<Model, Sector, Object> pair = findModelAndSectorById(dto.getModelId(), dto.getSectorId());

        motoMapper.updateEntityFromDTO(dto, moto);

        moto.setModel(pair.first());
        moto.setSector(pair.second());

        Moto updated = motoRepository.save(moto);

        return motoMapper.toResponseDTO(updated);
    }

    @Caching(evict = {
            @CacheEvict(value = "motos", key = "#motoId"),
            @CacheEvict(value = "motosAll", allEntries = true)
    })
    public void delete(Long motoId) {
        if (!motoRepository.existsById(motoId)) {
            throw new MotoNotFoundException(motoId);
        }
        motoRepository.deleteById(motoId);
    }

    // paginado e filtrado por placa ou setor
    public Page<MotoResponseDTO> getAllFiltered(String plate, Long sectorId, Pageable pageable) {
        if (plate != null && sectorId != null) {
            return motoRepository.findByPlateAndSector_SectorId(plate, sectorId, pageable)
                    .map(motoMapper::toResponseDTO);
        } else if (plate != null) {
            return motoRepository.findByPlate(plate, pageable)
                    .map(motoMapper::toResponseDTO);
        } else if (sectorId != null) {
            return motoRepository.findBySector_SectorId(sectorId, pageable)
                    .map(motoMapper::toResponseDTO);
        } else {
            return motoRepository.findAll(pageable)
                    .map(motoMapper::toResponseDTO);
        }
    }
}
