package com.mottu.mapping.model;

import com.mottu.mapping.model.enums.CorSetor;
import com.mottu.mapping.model.enums.TipoSetor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="TB_SETOR")
@NoArgsConstructor
@AllArgsConstructor
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_setor;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSetor tipo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CorSetor cor;
}
