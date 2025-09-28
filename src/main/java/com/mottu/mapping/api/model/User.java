package com.mottu.mapping.api.model;

import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="TB_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    @Column(name="password")
    private String password;

    private String role;
}
