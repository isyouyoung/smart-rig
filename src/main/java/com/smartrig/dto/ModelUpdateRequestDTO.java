package com.smartrig.dto;

public record ModelUpdateRequestDTO(

        Long modelId,
        String itemType,
        String manufacturer,
        String modelName,
        String modelNumber,
        String status

) {
}