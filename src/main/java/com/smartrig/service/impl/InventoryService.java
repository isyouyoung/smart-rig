package com.smartrig.service.impl;

import com.smartrig.dto.InventoryInRequestDTO;
import com.smartrig.dto.InventoryInResponseDTO;
import com.smartrig.exception.ModelNotFoundException;
import com.smartrig.repository.InventoryHistoryRepository;
import com.smartrig.repository.ModelRepository;
import com.smartrig.repository.StockRepository;
import com.smartrig.repository.entity.InventoryHistoryEntity;
import com.smartrig.repository.entity.StockEntity;
import com.smartrig.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryService {

    private final ModelRepository modelRepository;
    private final StockRepository stockRepository;
    private final InventoryHistoryRepository inventoryHistoryRepository;

    // 재고 증가와 기록 저장을 한 트랜잭션으로 묶는다.
    // 둘 중 하나라도 실패하면 둘 다 취소되어 재고와 장부가 어긋나지 않는다.
    @Transactional
    @Override
    public InventoryInResponseDTO stockIn(InventoryInRequestDTO dto) {

        // 1. 존재하는 부품인지 확인
        modelRepository.findById(dto.modelId())
                .orElseThrow(() ->
                        new ModelNotFoundException("입고할 모델이 존재하지 않습니다. ID: " + dto.modelId())
                );

        // 2. 재고 행 찾기. 아직 없으면 수량 0으로 새로 만든다.
        StockEntity stock = stockRepository.findByModelId(dto.modelId())
                .orElseGet(() -> stockRepository.save(
                        StockEntity.builder().modelId(dto.modelId()).build()
                ));

        // 3. 재고 증가 (저장은 트랜잭션이 끝날 때 자동으로 반영된다)
        stock.increase(dto.quantity());

        // 4. 입고 기록 저장
        InventoryHistoryEntity history = inventoryHistoryRepository.save(
                InventoryHistoryEntity.ofIn(
                        dto.modelId(),
                        dto.quantity(),
                        dto.unitPrice(),
                        dto.purchaseDate(),
                        dto.memo()
                )
        );

        return new InventoryInResponseDTO(
                history.getHistoryId(),
                dto.modelId(),
                dto.quantity(),
                stock.getQuantity()
        );
    }
}