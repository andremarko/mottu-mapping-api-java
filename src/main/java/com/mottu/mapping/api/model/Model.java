package com.mottu.mapping.api.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="TB_MODEL")
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long model_id;
    @Column(nullable = false)
    private String model_name;
}
