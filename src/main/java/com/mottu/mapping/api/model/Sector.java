package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="TB_SECTOR")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sector_id")
    private Long sectorId;
    @ManyToOne(cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name="yard_id", nullable = false)
    private MotoYard yard;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(name="color_rgb", nullable = false, length = 50)
    private String colorRgb;
    @Column(name="color_name", nullable = false, length = 50)
    private String colorName;
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private List<Moto> motos;
}
