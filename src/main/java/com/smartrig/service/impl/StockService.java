package com.smartrig.service.impl;

import com.smartrig.dto.StockCreateRequestDTO;
import com.smartrig.dto.StockResponseDTO;
import com.smartrig.dto.StockUpdateRequestDTO;
import com.smartrig.exception.StockNotFoundException;
import com.smartrig.mapper.StockMapper;
import com.smartrig.repository.StockRepository;
import com.smartrig.repository.entity.StockEntity;
import com.smartrig.service.IStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public StockResponseDTO saveStock(StockCreateRequestDTO dto) {

        StockEntity entity = StockMapper.toEntity(dto);

        StockEntity saved = stockRepository.save(entity);

        return StockMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public StockResponseDTO updateStock(StockUpdateRequestDTO dto) {

        StockEntity entity = stockRepository.findByModelId(dto.modelId())
                .orElseThrow(() ->
                        new StockNotFoundException("해당 재고가 없습니다.")
                );

        entity.updateQuantity(dto.quantity());

        return StockMapper.toDTO(entity);

    }

}
