package com.mottu.mapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "TB_UNIDADE")
@AllArgsConstructor
@NoArgsConstructor
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_unidade;
    @Column(unique = true, nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String logradouro;
    @Column(length = 5, nullable = false)
    private String numero;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String codigoPostal;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String uf;
}
