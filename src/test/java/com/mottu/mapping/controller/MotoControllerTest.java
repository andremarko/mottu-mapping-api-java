package com.mottu.mapping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mottu.mapping.api.controller.MotoController;
import com.mottu.mapping.api.dto.request.MotoRequestDTO;
import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.exception.MotoNotFoundException;
import com.mottu.mapping.api.service.MotoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MotoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment environment;

    @MockitoBean
    private MotoService motoService;

    String plate = "MOTO001";
    Long modelId = 1L;
    Long sectorId = 1L;
    String coordinates = "coordinates";

    private ModelResponseDTO buildModel() {
        return new ModelResponseDTO(modelId, "Modelo X");
    }

    private SectorResponseDTO buildSector() {
        return new SectorResponseDTO(
                sectorId,
                1L,
                "Setor A",
                "Descrição do setor",
                "#FF0000",
                "Vermelho"
        );
    }

    private MotoResponseDTO buildMotoResponse(Long id, String plate) {
        return new MotoResponseDTO(
                id,
                plate,
                coordinates,
                buildModel(),
                buildSector()
        );
    }

    @Test
    void create_shouldReturnCreatedMoto() throws Exception {
        MotoRequestDTO request = new MotoRequestDTO();
        request.setPlate(plate);
        request.setModelId(modelId);
        request.setSectorId(sectorId);
        request.setCoordinates(coordinates);

        MotoResponseDTO response = buildMotoResponse(1L, plate);

        Mockito.when(motoService.save(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/motos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.motorcycleId").value(1))
                .andExpect(jsonPath("$.plate").value("MOTO001"))
                .andExpect(jsonPath("$.model.modelId").value(1))
                .andExpect(jsonPath("$.sector.sectorId").value(1));
    }

    @Test
    void getAll_shouldReturnPagedMotos() throws Exception {
        MotoResponseDTO moto1 = buildMotoResponse(1L, "MOTO001");
        MotoResponseDTO moto2 = buildMotoResponse(2L, "MOTO002");

        Page<MotoResponseDTO> page = new PageImpl<>(List.of(moto1, moto2));

        Mockito.when(motoService.getAllFiltered(Mockito.any(), Mockito.any(), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/motos")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].plate").value("MOTO001"))
                .andExpect(jsonPath("$.content[0].model.modelId").value(1))
                .andExpect(jsonPath("$.content[0].sector.sectorId").value(1));
    }

    @Test
    void getById_shouldReturnMoto() throws Exception {
        MotoResponseDTO response = buildMotoResponse(1L, "MOTO001");

        Mockito.when(motoService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/motos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motorcycleId").value(1))
                .andExpect(jsonPath("$.plate").value("MOTO001"))
                .andExpect(jsonPath("$.model.modelId").value(1))
                .andExpect(jsonPath("$.sector.sectorId").value(1));
    }

    @Test
    void getById_shouldReturn404() throws Exception {
        Mockito.when(motoService.getById(999L))
                .thenThrow(new MotoNotFoundException(999L));

        mockMvc.perform(get("/api/motos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnUpdatedMoto() throws Exception {
        MotoRequestDTO request = new MotoRequestDTO();
        request.setPlate("MOTO001");
        request.setModelId(modelId);
        request.setSectorId(sectorId);
        request.setCoordinates(coordinates);

        MotoResponseDTO response = buildMotoResponse(1L, "MOTO001");

        Mockito.when(motoService.update(Mockito.eq(1L), Mockito.any())).thenReturn(response);

        mockMvc.perform(put("/api/motos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plate").value("MOTO001"))
                .andExpect(jsonPath("$.model.modelId").value(1))
                .andExpect(jsonPath("$.sector.sectorId").value(1))
                .andExpect(jsonPath("$.coordinates").value("coordinates"));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(motoService).delete(1L);

        mockMvc.perform(delete("/api/motos/1"))
                .andExpect(status().isNoContent());
    }
}
