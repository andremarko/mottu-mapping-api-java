package com.mottu.mapping.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OperatorController {

    @GetMapping("/operator/login")
    public String getLogin(Model model) {
        // Model User
        //model.addAttribute("user", new User());
        return "operator/login";
    }

}
