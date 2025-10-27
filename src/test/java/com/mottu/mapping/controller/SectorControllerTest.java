package com.mottu.mapping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mottu.mapping.api.controller.SectorController;
import com.mottu.mapping.api.dto.request.SectorRequestDTO;
import com.mottu.mapping.api.dto.response.SectorResponseDTO;
import com.mottu.mapping.api.service.SectorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SectorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SectorService sectorService;

    private SectorRequestDTO buildRequest() {
        SectorRequestDTO request = new SectorRequestDTO();
        request.setYardId(1L);
        request.setName("Sector A");
        request.setDescription("Descrição do setor A");
        request.setColorRgb("#FF0000");
        request.setColorName("Red");
        return request;
    }

    private SectorResponseDTO buildResponse(Long id) {
        SectorResponseDTO response = new SectorResponseDTO();
        response.setSectorId(id);
        response.setYardId(1L);
        response.setName("Sector A");
        response.setDescription("Descrição do setor A");
        response.setColorRgb("#FF0000");
        response.setColorName("Red");
        return response;
    }

    @Test
    void create_shouldReturnCreatedSector() throws Exception {
        SectorRequestDTO request = buildRequest();
        SectorResponseDTO response = buildResponse(1L);

        Mockito.when(sectorService.save(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/sectors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sectorId").value(1))
                .andExpect(jsonPath("$.yardId").value(1))
                .andExpect(jsonPath("$.name").value("Sector A"))
                .andExpect(jsonPath("$.description").value("Descrição do setor A"))
                .andExpect(jsonPath("$.colorRgb").value("#FF0000"))
                .andExpect(jsonPath("$.colorName").value("Red"));
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        SectorResponseDTO sector1 = buildResponse(1L);
        SectorResponseDTO sector2 = buildResponse(2L);
        sector2.setName("Sector B");
        sector2.setDescription("Descrição do setor B");
        sector2.setColorRgb("#00FF00");
        sector2.setColorName("Green");

        Page<SectorResponseDTO> page = new PageImpl<>(List.of(sector1, sector2));

        Mockito.when(sectorService.getAll(Mockito.any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/sectors")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Sector A"))
                .andExpect(jsonPath("$.content[1].name").value("Sector B"))
                .andExpect(jsonPath("$.content[1].colorName").value("Green"));
    }

    @Test
    void getById_shouldReturnSector() throws Exception {
        SectorResponseDTO response = buildResponse(1L);

        Mockito.when(sectorService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/sectors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sectorId").value(1))
                .andExpect(jsonPath("$.name").value("Sector A"))
                .andExpect(jsonPath("$.description").value("Descrição do setor A"))
                .andExpect(jsonPath("$.colorName").value("Red"));
    }

    @Test
    void update_shouldReturnUpdatedSector() throws Exception {
        SectorRequestDTO request = buildRequest();
        request.setName("Sector A Updated");
        request.setDescription("Descrição atualizada");
        request.setColorRgb("#0000FF");
        request.setColorName("Blue");

        SectorResponseDTO response = buildResponse(1L);
        response.setName("Sector A Updated");
        response.setDescription("Descrição atualizada");
        response.setColorRgb("#0000FF");
        response.setColorName("Blue");

        Mockito.when(sectorService.update(Mockito.eq(1L), Mockito.any())).thenReturn(response);

        mockMvc.perform(put("/api/sectors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sector A Updated"))
                .andExpect(jsonPath("$.description").value("Descrição atualizada"))
                .andExpect(jsonPath("$.colorName").value("Blue"));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(sectorService).delete(1L);

        mockMvc.perform(delete("/api/sectors/1"))
                .andExpect(status().isNoContent());
    }
}
