package com.smartrig.controller;

import com.smartrig.dto.InventoryInRequestDTO;
import com.smartrig.dto.InventoryInResponseDTO;
import com.smartrig.service.IInventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/inventory/v1")
public class InventoryController {

    private final IInventoryService inventoryService;

    public InventoryController(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // POST /inventory/v1/stockIn : 부품 입고 (재고 증가 + 입고 기록 저장)
    @PostMapping("/stockIn")
    public InventoryInResponseDTO stockIn(
            @Valid @RequestBody InventoryInRequestDTO dto) {

        return inventoryService.stockIn(dto);
    }
}