package com.mottu.mapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="TB_PATIO")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Patio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_patio;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Integer capacidade;
}
