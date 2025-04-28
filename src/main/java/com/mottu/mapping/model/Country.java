package com.mottu.mapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TB_COUNTRY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String country_id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable=false, length = 2, unique = true)
    private String iso_code;
}
