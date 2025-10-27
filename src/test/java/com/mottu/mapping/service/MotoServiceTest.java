package com.mottu.mapping.service;

import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.mapper.MotoMapper;
import com.mottu.mapping.api.model.Moto;
import com.mottu.mapping.api.model.Model;
import com.mottu.mapping.api.model.Sector;
import com.mottu.mapping.api.repository.MotoRepository;
import com.mottu.mapping.api.service.MotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MotoServiceTest {

    @Mock
    private MotoRepository motoRepository;

    @Mock
    private MotoMapper motoMapper;

    @InjectMocks
    private MotoService motoService;

    private Moto moto;
    private MotoResponseDTO motoResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        moto = new Moto();
        moto.setMotorcycleId(1L);
        motoResponseDTO = new MotoResponseDTO();
        motoResponseDTO.setMotorcycleId(1L);
    }

    @Test
    void getAllFiltered_plateAndSector_shouldReturnMappedPage() {
        Page<Moto> motoPage = new PageImpl<>(List.of(moto));
        when(motoRepository.findByPlateAndSector_SectorId("MOTO001", 1L, PageRequest.of(0, 10)))
                .thenReturn(motoPage);
        when(motoMapper.toResponseDTO(moto)).thenReturn(motoResponseDTO);

        Page<MotoResponseDTO> result = motoService.getAllFiltered("MOTO001", 1L, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(motoRepository).findByPlateAndSector_SectorId("MOTO001", 1L, PageRequest.of(0, 10));
    }

    @Test
    void getAllFiltered_onlyPlate_shouldReturnMappedPage() {
        Page<Moto> motoPage = new PageImpl<>(List.of(moto));
        when(motoRepository.findByPlate("MOTO001", PageRequest.of(0, 10)))
                .thenReturn(motoPage);
        when(motoMapper.toResponseDTO(moto)).thenReturn(motoResponseDTO);

        Page<MotoResponseDTO> result = motoService.getAllFiltered("MOTO001", null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(motoRepository).findByPlate("MOTO001", PageRequest.of(0, 10));
    }

    @Test
    void getAllFiltered_onlySector_shouldReturnMappedPage() {
        Page<Moto> motoPage = new PageImpl<>(List.of(moto));
        when(motoRepository.findBySector_SectorId(1L, PageRequest.of(0, 10)))
                .thenReturn(motoPage);
        when(motoMapper.toResponseDTO(moto)).thenReturn(motoResponseDTO);

        Page<MotoResponseDTO> result = motoService.getAllFiltered(null, 1L, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(motoRepository).findBySector_SectorId(1L, PageRequest.of(0, 10));
    }

    @Test
    void getAllFiltered_noFilters_shouldReturnMappedPage() {
        Page<Moto> motoPage = new PageImpl<>(List.of(moto));
        when(motoRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(motoPage);
        when(motoMapper.toResponseDTO(moto)).thenReturn(motoResponseDTO);

        Page<MotoResponseDTO> result = motoService.getAllFiltered(null, null, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(motoRepository).findAll(PageRequest.of(0, 10));
    }
}