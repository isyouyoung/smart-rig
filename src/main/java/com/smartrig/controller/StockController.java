package com.smartrig.controller;

import com.smartrig.dto.StockResponseDTO;
import com.smartrig.service.IStockService;
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

}