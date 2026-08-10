package com.smartrig.service;

import com.smartrig.dto.StockCreateRequestDTO;
import com.smartrig.dto.StockResponseDTO;
import com.smartrig.dto.StockUpdateRequestDTO;

public interface IStockService {

    // modelId로 재고 조회
    StockResponseDTO getStockByModelId(Long modelId);

    // 재고 생성
    StockResponseDTO saveStock(StockCreateRequestDTO dto);

    // 재고 수정
    StockResponseDTO updateStock(StockUpdateRequestDTO dto);

}
