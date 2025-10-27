package com.mottu.mapping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mottu.mapping.api.controller.ModelController;
import com.mottu.mapping.api.dto.request.ModelRequestDTO;
import com.mottu.mapping.api.dto.response.ModelResponseDTO;
import com.mottu.mapping.api.exception.ModelNotFoundException;
import com.mottu.mapping.api.service.ModelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ModelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModelService modelService;

    private ModelResponseDTO buildModel(Long id, String name) {
        return new ModelResponseDTO(id, name);
    }

    private ModelRequestDTO buildModelRequest(String name) {
        ModelRequestDTO request = new ModelRequestDTO();
        request.setModelName(name);
        return request;
    }

    @Test
    void create_shouldReturnCreatedModel() throws Exception {
        ModelRequestDTO request = buildModelRequest("Mottu Pop");
        ModelResponseDTO response = buildModel(1L, "Mottu Pop");

        Mockito.when(modelService.save(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.modelId").value(1))
                .andExpect(jsonPath("$.modelName").value("Mottu Pop"));
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        ModelResponseDTO model1 = buildModel(1L, "Mottu Pop");
        ModelResponseDTO model2 = buildModel(2L, "Mottu Sport");

        Mockito.when(modelService.getAll()).thenReturn(List.of(model1, model2));

        mockMvc.perform(get("/api/models"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].modelName").value("Mottu Pop"))
                .andExpect(jsonPath("$[1].modelName").value("Mottu Sport"));
    }

    @Test
    void getById_shouldReturnModel() throws Exception {
        ModelResponseDTO response = buildModel(1L, "Mottu E");

        Mockito.when(modelService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/models/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelId").value(1))
                .andExpect(jsonPath("$.modelName").value("Mottu E"));
    }

    @Test
    void getById_shouldReturn404WhenNotFound() throws Exception {
        Mockito.when(modelService.getById(999L))
                .thenThrow(new ModelNotFoundException(999L));

        mockMvc.perform(get("/api/models/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnUpdatedModel() throws Exception {
        ModelRequestDTO request = buildModelRequest("Mottu Pop");
        ModelResponseDTO response = buildModel(1L, "Mottu Pop");

        Mockito.when(modelService.update(Mockito.eq(1L), Mockito.any())).thenReturn(response);

        mockMvc.perform(put("/api/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelName").value("Mottu Pop"));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(modelService).delete(1L);

        mockMvc.perform(delete("/api/models/1"))
                .andExpect(status().isNoContent());
    }
}
