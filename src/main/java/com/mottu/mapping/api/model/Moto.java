package com.mottu.mapping.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Table(name="TB_MOTORCYCLE")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long motorcycle_id;
    @Column(nullable = false, unique = true)
    private String plate;
    @Column(length = 17, nullable = false, unique = true)
    private String chassis;
    @Column(nullable = false)
    private LocalDateTime entry;
    @Column(nullable = true, length = 256)
    private String description;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="model_id")
    private Model model;
    @ManyToOne
    @JoinColumn(name="sector_id")
    private Sector sector;
}
