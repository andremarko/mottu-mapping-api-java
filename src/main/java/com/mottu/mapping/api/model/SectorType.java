package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="TB_SECTOR_TYPE")
@Getter
@Setter
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
