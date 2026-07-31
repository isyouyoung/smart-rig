package com.smartrig.mapper;

import com.smartrig.dto.StockCreateRequestDTO;
import com.smartrig.dto.StockResponseDTO;
import com.smartrig.repository.entity.StockEntity;

public class StockMapper {

    public static StockResponseDTO toDTO(StockEntity entity) {

        return new StockResponseDTO(
                entity.getStockId(),
                entity.getModelId(),
                entity.getQuantity(),
                entity.getRegDt(),
                entity.getUpdDt()
        );
    }

    public static StockEntity toEntity(StockCreateRequestDTO dto) {

        return StockEntity.builder()
                .modelId(dto.modelId())
                .quantity(dto.quantity())
                .build();
    }

}