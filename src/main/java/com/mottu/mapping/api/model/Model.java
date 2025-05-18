package com.mottu.mapping.api.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="TB_MODEL")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="model_id")
    private Long modelId;
    @Column(nullable = false)
    private String modelName;

    public Model() {}

    public Model(String modelName) {
        this.modelName = modelName;
    }
}
