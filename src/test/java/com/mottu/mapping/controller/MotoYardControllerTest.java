package com.mottu.mapping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mottu.mapping.api.controller.MotoYardController;
import com.mottu.mapping.api.dto.request.MotoYardRequestDTO;
import com.mottu.mapping.api.dto.response.MotoYardResponseDTO;
import com.mottu.mapping.api.exception.MotoYardNotFoundException;
import com.mottu.mapping.api.service.MotoYardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MotoYardController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MotoYardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MotoYardService motoYardService;

    private MotoYardRequestDTO buildRequest() {
        MotoYardRequestDTO request = new MotoYardRequestDTO();
        request.setBranchName("Main Yard");
        request.setAddress("Rua das Motos, 123");
        request.setCity("São Paulo");
        request.setState("SP");
        request.setDescription("Yard principal");
        request.setCapacity(100);
        return request;
    }

    private MotoYardResponseDTO buildResponse(Long id) {
        MotoYardResponseDTO response = new MotoYardResponseDTO();
        response.setYardId(id);
        response.setBranchName("Main Yard");
        response.setAddress("Rua das Motos, 123");
        response.setCity("São Paulo");
        response.setState("SP");
        response.setDescription("Yard principal");
        response.setCapacity(100);
        return response;
    }

    @Test
    void create_shouldReturnCreatedMotoYard() throws Exception {
        MotoYardRequestDTO request = buildRequest();
        MotoYardResponseDTO response = buildResponse(1L);

        Mockito.when(motoYardService.save(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/motoyards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.yardId").value(1))
                .andExpect(jsonPath("$.branchName").value("Main Yard"))
                .andExpect(jsonPath("$.address").value("Rua das Motos, 123"))
                .andExpect(jsonPath("$.city").value("São Paulo"))
                .andExpect(jsonPath("$.state").value("SP"))
                .andExpect(jsonPath("$.description").value("Yard principal"))
                .andExpect(jsonPath("$.capacity").value(100));
    }

    @Test
    void getAll_shouldReturnPagedMotoYards() throws Exception {
        MotoYardResponseDTO yard1 = buildResponse(1L);
        MotoYardResponseDTO yard2 = buildResponse(2L);

        yard2.setBranchName("Secondary Yard");
        yard2.setAddress("Av. das Motos, 456");
        yard2.setDescription("Yard secundário");

        Page<MotoYardResponseDTO> page = new PageImpl<>(List.of(yard1, yard2));

        Mockito.when(motoYardService.getAll(Mockito.any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/motoyards")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].branchName").value("Main Yard"))
                .andExpect(jsonPath("$.content[1].branchName").value("Secondary Yard"))
                .andExpect(jsonPath("$.content[1].address").value("Av. das Motos, 456"));
    }

    @Test
    void getById_shouldReturnMotoYard() throws Exception {
        MotoYardResponseDTO response = buildResponse(1L);

        Mockito.when(motoYardService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/motoyards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yardId").value(1))
                .andExpect(jsonPath("$.branchName").value("Main Yard"))
                .andExpect(jsonPath("$.address").value("Rua das Motos, 123"))
                .andExpect(jsonPath("$.city").value("São Paulo"))
                .andExpect(jsonPath("$.state").value("SP"))
                .andExpect(jsonPath("$.description").value("Yard principal"))
                .andExpect(jsonPath("$.capacity").value(100));
    }

    @Test
    void getById_shouldReturn404() throws Exception {
        Mockito.when(motoYardService.getById(999L))
                .thenThrow(new MotoYardNotFoundException(999L));

        mockMvc.perform(get("/api/motoyards/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnUpdatedMotoYard() throws Exception {
        MotoYardRequestDTO request = buildRequest();
        request.setBranchName("Main Yard Updated");
        request.setDescription("Yard atualizado");

        MotoYardResponseDTO response = buildResponse(1L);
        response.setBranchName("Main Yard Updated");
        response.setDescription("Yard atualizado");

        Mockito.when(motoYardService.update(Mockito.eq(1L), Mockito.any())).thenReturn(response);

        mockMvc.perform(put("/api/motoyards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.branchName").value("Main Yard Updated"))
                .andExpect(jsonPath("$.description").value("Yard atualizado"));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(motoYardService).delete(1L);

        mockMvc.perform(delete("/api/motoyards/1"))
                .andExpect(status().isNoContent());
    }
}
