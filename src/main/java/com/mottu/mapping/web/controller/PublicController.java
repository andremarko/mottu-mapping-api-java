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
public class PublicController {

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/about")
    public String getAbout() {return "about";}
}
