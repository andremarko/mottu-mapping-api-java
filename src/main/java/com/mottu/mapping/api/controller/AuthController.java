package com.mottu.mapping.api.controller;

import com.mottu.mapping.api.dto.request.LoginRequestDTO;
import com.mottu.mapping.api.security.UserDetailServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @Autowired
    private UserDetailServiceImplementation userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(loginRequestDTO.getUsername());
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.ok(
                        new LoginResponseDTO("Login realizado com sucesso!", userDetails.getAuthorities().toString())
                );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponseDTO("Usuário ou senha incorretos.", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginResponseDTO("Usuário ou senha incorretos.", null));
        }
    }
}
