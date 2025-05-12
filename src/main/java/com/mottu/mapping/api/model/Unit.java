package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "TB_UNIT")
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unit_id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String address;
    @Column(length = 5, nullable = false)
    private String number;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String zipCode;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<MotoYard> motoyards;
}
