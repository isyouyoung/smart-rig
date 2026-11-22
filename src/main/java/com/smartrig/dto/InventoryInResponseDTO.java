package com.smartrig.dto;

public record InventoryInResponseDTO(

        Long historyId,       // 저장된 입고 기록 번호
        Long modelId,         // 입고한 부품
        Integer inQuantity,   // 이번에 입고한 수량
        Integer currentStock  // 입고 후 현재 총 재고
) {
}