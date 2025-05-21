package com.mottu.mapping.api.repository;

import com.mottu.mapping.api.model.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Page<Moto> findByPlate(String plate, Pageable pageable);
    Page<Moto> findBySector_SectorId(Long sectorId, Pageable pageable);
    Page<Moto> findByPlateAndSector_SectorId(String plate, Long sectorId, Pageable pageable);

}
