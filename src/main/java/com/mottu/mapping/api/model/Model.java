package com.mottu.mapping.api.model;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="TB_MODEL")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="model_id")
    private Long modelId;

    @Column(name="model_name", nullable = false)
    private String modelName;
}
