package com.smartrig.dto;

public record CpuStockResponseDTO(

        Long modelId,

        String manufacturer,

        String series,

        String generation,

        String socket,

        String modelName,

        String modelNumber,

        Integer quantity

) {}