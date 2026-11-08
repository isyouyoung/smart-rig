package com.smartrig.dto;

import com.smartrig.repository.entity.CompletePcEntity;
import com.smartrig.repository.entity.PcStatus;
import lombok.Getter;

@Getter
public class CompletePcResponseDTO {

    private final Long pcId;
    private final String name;
    private final String specs;
    private final Integer price;
    private final PcStatus status;

    private CompletePcResponseDTO(CompletePcEntity entity) {
        this.pcId = entity.getPcId();
        this.name = entity.getName();
        this.specs = entity.getSpecs();
        this.price = entity.getPrice();
        this.status = entity.getStatus();
    }

    public static CompletePcResponseDTO from(CompletePcEntity entity) {
        return new CompletePcResponseDTO(entity);
    }
}
