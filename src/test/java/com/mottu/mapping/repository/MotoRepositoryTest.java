package com.mottu.mapping.repository;

import com.mottu.mapping.api.repository.MotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MotoRepositoryTest {

    @Autowired
    private MotoRepository motoRepository;

    @Test
    void findByPlate_shouldReturnMoto() {
        Page<?> result = motoRepository.findByPlate("MOTO001", PageRequest.of(0, 10));
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0, "Should return moto with plate MOTO001");
    }

    @Test
    void findBySector_SectorId_shouldReturnMotos() {
        Page<?> result = motoRepository.findBySector_SectorId(1L, PageRequest.of(0, 10));
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0, "Should return motos from sector 1");
    }

    @Test
    void findBySector_Yard_YardId_shouldReturnMotos() {
        Page<?> result = motoRepository.findBySector_Yard_YardId(1L, PageRequest.of(0, 10));
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0, "Should return motos from yard 1");
    }

    @Test
    void findByPlateAndSector_SectorId_shouldReturnMotos() {
        Page<?> result = motoRepository.findByPlateAndSector_SectorId("MOTO001", 4L, PageRequest.of(0, 10));
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0, "Should return moto MOTO001 from sector_id 4");
    }
}
