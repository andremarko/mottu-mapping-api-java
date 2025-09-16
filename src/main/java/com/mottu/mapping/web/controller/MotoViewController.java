package com.mottu.mapping.web.controller;

import com.mottu.mapping.api.dto.response.MotoResponseDTO;
import com.mottu.mapping.api.service.MotoYardService;
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

    @Autowired
    private MotoYardService motoYardService;


    // security admin e operator
    // pageable -> ajustar
    @GetMapping("/motoyards")
    public String getMotoYards(Model model) {
        model.addAttribute("motoYardPage", motoYardService.getAll());
        return "motoyards/list";
    }

    // paginado
    @GetMapping("/sectors")
    public String getSectors(){
        return "sectors/list";
    }


    // private
    // security admin e operator
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













    @GetMapping
    public String {}




    // public
    @GetMapping("/")
    public String getHome() {
        return "index";
    }



}
