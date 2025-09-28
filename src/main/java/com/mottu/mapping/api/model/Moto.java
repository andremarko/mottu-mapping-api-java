package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="TB_MOTORCYCLE")
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="motorcycle_id")
    private Long motorcycleId;
    @Column(nullable = false, unique = true)
    private String plate;
    @Column(nullable = false)
    private String coordinates;

    // Lazy evita que o JPA carregue o objeto completo imediatamente
    // Cascade PERSIST só persiste o model se ele for novo (sem id)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="model_id")
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sector_id")
    private Sector sector;
}
