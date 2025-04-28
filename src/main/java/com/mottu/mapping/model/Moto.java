package com.mottu.mapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Table(name="TB_MOTORCYCLE")
@Entity
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
    @Column(nullable = false, length = 256)
    private String description;
    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;
    @ManyToOne
    @JoinColumn(name="sector_id")
    private Sector sector;
}
