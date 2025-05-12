package com.mottu.mapping.api.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SectorDTO {
    @NotNull
    private Long sector_id;
    @NotNull
    private MotoYardDTO yard;
    @NotNull
    private SectorTypeDTO type;
}
