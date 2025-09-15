package com.mottu.mapping.web.controller;

import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import org.springframework.ui.Model;
import com.mottu.mapping.api.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MotoViewController {

    @Autowired
    private MotoService motoService;

    @GetMapping("/motos")
    public String getMotos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("plate").ascending());
        Page<MotoResponseDTO> motosPage = motoService.getAll(pageable);

        model.addAttribute("motosPage", motosPage);
        return "motos/list";
    }
}
