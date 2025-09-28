    package com.mottu.mapping.api.dto.response;

    import lombok.*;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public class SectorResponseDTO {
        private Long sectorId;
        private Long yardId;
        private String name;
        private String description;
        private String colorRgb;
        private String colorName;
    }
