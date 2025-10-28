package com.mottu.mapping.api.controller;

import com.mottu.mapping.api.dto.request.LoginRequestDTO;

import com.mottu.mapping.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Profile("oracle")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
//        String activeProfile = System.getProperty("spring.profiles.active");
//        if (!"oracle".equals(activeProfile)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        String username = loginRequestDTO.getUsername();
//        String password = loginRequestDTO.getPassword();
//
//        boolean valid = userService.validateUser(username, password);
//
//        if (valid) {
//            return ResponseEntity.ok("User logged in successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
//    }
}
