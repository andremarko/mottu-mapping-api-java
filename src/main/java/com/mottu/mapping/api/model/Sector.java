package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="TB_SECTOR")
@NoArgsConstructor
@AllArgsConstructor
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sector_id;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name= "yard_id", nullable = false)
    private MotoYard yard;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sector_type_id", nullable = false)
    private SectorType type;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private List<Moto> motos;
}
