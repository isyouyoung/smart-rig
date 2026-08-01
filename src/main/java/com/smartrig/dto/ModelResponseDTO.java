package com.smartrig.dto;

import java.time.LocalDateTime;

public record ModelResponseDTO(
        Long modelId,
        String itemType,
        String manufacturer,
        String series,
        String generation,
        String modelName,
        String modelNumber,
        String status,
        LocalDateTime regDt,
        LocalDateTime updDt
) {}
