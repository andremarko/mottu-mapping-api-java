package com.mottu.mapping.web.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/login")
    public String getLogin(Model model) {
        // Model User
        //model.addAttribute("user", new User());
        return "admin/login";
    }
}
