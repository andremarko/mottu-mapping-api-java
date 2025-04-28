package com.mottu.mapping.model;
import com.mottu.mapping.model.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
    @OneToMany(mappedBy = "yard")
    private List<Sector> sectors;
}
