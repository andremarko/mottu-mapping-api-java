package com.mottu.mapping.api.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="TB_MOTO_YARD")
public class MotoYard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="yard_id")
    private Long yardId;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer capacity;
    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sector> sectors;
}
