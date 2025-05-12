package com.mottu.mapping.api.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Table(name="TB_MOTO_YARD")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MotoYard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yard_id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer capacity;
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL)
    private List<Sector> sectors;
}
