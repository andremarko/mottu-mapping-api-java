package com.mottu.mapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TB_SECTOR_TYPE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sector_type_id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(nullable = false, length = 100, unique = true)
    private String color;
}
