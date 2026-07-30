package com.smartrig.dto;

import java.time.LocalDateTime;

public record StockResponseDTO(

        Long stockId,
        Long modelId,
        Integer quantity,
        LocalDateTime regDt,
        LocalDateTime updDt

) {
}