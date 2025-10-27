package com.mottu.mapping.repository;

import com.mottu.mapping.api.repository.SectorRepository;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SectorRepositoryTest {

    @Autowired
    private SectorRepository sectorRepository;

    @Test
    void findByYard_YardIdShouldReturnSectors() {
        var result = sectorRepository.findByYard_YardId(1L);
        assertNotNull(result, "List of sectors should not be null");
        assertFalse(result.isEmpty(), "Should exist at least one sector at Yard 1");
    }

}
