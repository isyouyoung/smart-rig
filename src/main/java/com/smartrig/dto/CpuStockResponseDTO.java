package com.smartrig.dto;

public record CpuStockResponseDTO(

        Long modelId,

        String manufacturer,

        String series,

        String generation,

        String modelName,

        String modelNumber,

        Integer quantity

) {}