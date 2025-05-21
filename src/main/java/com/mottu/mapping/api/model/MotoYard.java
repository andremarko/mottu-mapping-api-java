package com.mottu.mapping.api.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Table(name="TB_MOTO_YARD")
@Entity
public class MotoYard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="yard_id")
    private Long yardId;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer capacity;
    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL)
    private List<Sector> sectors;

    public MotoYard() {}

    public MotoYard(String description, Integer capacity, List<Sector> sectors) {
        this.description = description;
        this.capacity = capacity;
        this.sectors = sectors;
    }
}
