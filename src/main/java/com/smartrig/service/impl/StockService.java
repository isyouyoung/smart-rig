package com.smartrig.service.impl;

import com.smartrig.dto.StockResponseDTO;
import com.smartrig.exception.StockNotFoundException;
import com.smartrig.mapper.StockMapper;
import com.smartrig.repository.StockRepository;
import com.smartrig.repository.entity.StockEntity;
import com.smartrig.service.IStockService;
import org.springframework.stereotype.Service;

@Service
public class StockService implements IStockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public StockResponseDTO getStockByModelId(Long modelId) {

        StockEntity entity = stockRepository.findByModelId(modelId)
                .orElseThrow(() ->
                        new StockNotFoundException("해당 재고가 없습니다.")
                );

        return StockMapper.toDTO(entity);
    }

}
