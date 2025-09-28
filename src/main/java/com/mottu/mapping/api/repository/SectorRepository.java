package com.mottu.mapping.api.repository;

import com.mottu.mapping.api.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findByYard_YardId(Long yardId);
}

