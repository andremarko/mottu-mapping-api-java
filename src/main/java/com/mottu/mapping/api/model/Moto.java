package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name="TB_MOTORCYCLE")
@Entity
@Data
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="motorcycle_id")
    private Long motorcycleId;
    @Column(nullable = false, unique = true)
    private String plate;
    @Column(nullable = false, unique = true)
    private String coordinates;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="model_Id")
    private Model model;
    @ManyToOne
    @JoinColumn(name="sector_id")
    private Sector sector;

    public Moto() {}

    public Moto(String plate, String coordinates, Model model, Sector sector) {
        this.plate = plate;
        this.coordinates = coordinates;
        this.model = model;
        this.sector = sector;
    }
}
