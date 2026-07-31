package com.smartrig.service;

import com.smartrig.dto.StockCreateRequestDTO;
import com.smartrig.dto.StockResponseDTO;

public interface IStockService {

    // modelId로 재고 조회
    StockResponseDTO getStockByModelId(Long modelId);

    // 재고 생성
    StockResponseDTO saveStock(StockCreateRequestDTO dto);

}
