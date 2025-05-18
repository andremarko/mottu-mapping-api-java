package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name="TB_SECTOR")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sector_id")
    private Long sectorId;
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="yard_id", nullable = false)
    private MotoYard yard;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(name="color_rgb", nullable = false, length = 50, unique = true)
    private String colorRgb;
    @Column(name="color_name", nullable = false, length = 50, unique = true)
    private String colorName;
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private List<Moto> motos;

    public Sector() {}

    public Sector(MotoYard yard, String name, String description, String colorRgb, String colorName, List<Moto> motos) {
        this.yard = yard;
        this.name = name;
        this.description = description;
        this.colorRgb = colorRgb;
        this.colorName = colorName;
        this.motos = motos;
    }
}
