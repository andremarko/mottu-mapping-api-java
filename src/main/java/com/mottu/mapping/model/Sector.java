package com.mottu.mapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="TB_SECTOR")
@NoArgsConstructor
@AllArgsConstructor
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sector_id;

    @ManyToOne
    @JoinColumn(name= "yard_id", nullable = false)
    private MotoYard yard;

    @ManyToOne
    @JoinColumn(name = "sector_type_id", nullable = false)
    private SectorType type;

    @OneToMany(mappedBy = "sector")
    private List<Moto> motos;
}
