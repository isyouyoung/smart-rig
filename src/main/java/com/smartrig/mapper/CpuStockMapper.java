package com.smartrig.mapper;

import com.smartrig.dto.CpuStockResponseDTO;
import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.repository.entity.StockEntity;

public class CpuStockMapper {

    public static CpuStockResponseDTO toDTO(
            ModelEntity model,
            StockEntity stock
    ) {

        // 삼항 연산자 조건 ? 참일때 : 거짓일때
        // STOCK 이 있으면 stock.getQuantity()를 사용하고
        // 없으면 null을 넣어라
        Integer quantity = (stock != null)
                ? stock.getQuantity()
                : null;

        return new CpuStockResponseDTO(
                model.getModelId(),
                model.getManufacturer(),
                model.getSeries(),
                model.getGeneration(),
                model.getModelName(),
                model.getModelNumber(),
                quantity
        );
    }

}
