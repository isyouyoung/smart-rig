package com.smartrig.service;

import com.smartrig.dto.InventoryInRequestDTO;
import com.smartrig.dto.InventoryInResponseDTO;

public interface IInventoryService {

    // 부품 입고: 재고를 늘리고 입고 기록을 남긴다.
    InventoryInResponseDTO stockIn(InventoryInRequestDTO dto);
}