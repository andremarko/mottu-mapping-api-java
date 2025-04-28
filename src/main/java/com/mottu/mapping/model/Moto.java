package com.mottu.mapping.model;

import com.mottu.mapping.model.enums.NomeModelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Table(name="TB_MOTO")
@Entity()
@AllArgsConstructor
@NoArgsConstructor
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_moto;
    @Column(nullable=false, name="nome_modelo")
    @Enumerated(EnumType.STRING)
    private NomeModelo nomeModelo;
    @Column(length = 8, nullable = false, unique = true)
    private String placa;
    @Column(length = 17, nullable = false, unique = true)
    private String chassis;
    @Column(nullable = false)
    private LocalDateTime entrada;
    @Column(nullable = false, length = 256)
    private String descricao;
}
