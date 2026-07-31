package com.smartrig.controller;

import com.smartrig.dto.StockCreateRequestDTO;
import com.smartrig.dto.StockResponseDTO;
import com.smartrig.service.IStockService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock/v1")
public class StockController {

    private final IStockService stockService;

    public StockController(IStockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/getStockByModelId")
    public StockResponseDTO getStockByModelId(
            @RequestParam Long modelId) {

        return stockService.getStockByModelId(modelId);
    }

    @PostMapping("/saveStock")
    public StockResponseDTO saveStock(
            @Valid @RequestBody StockCreateRequestDTO dto) {

        return stockService.saveStock(dto);
    }

}