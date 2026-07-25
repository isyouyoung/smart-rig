package com.smartrig.mapper;

import com.smartrig.dto.ModelCreateRequestDTO;
import com.smartrig.dto.ModelResponseDTO;
import com.smartrig.repository.entity.ModelEntity;

// 역활은 단 한가지
// "DTO와 Entity 변환 담당"
public class ModelMapper {

    // DTO -> Entity 변환
    public static ModelEntity toEntity(ModelCreateRequestDTO dto) {

        return ModelEntity.builder()
                .itemType(dto.itemType())
                .manufacturer(dto.manufacturer())
                .modelName(dto.modelName())
                .modelNumber(dto.modelNumber())
                .build();
    }

    // Entity -> DTO 변환
    public static ModelResponseDTO toDTO(ModelEntity entity) {

        return new ModelResponseDTO(
                entity.getModelId(),
                entity.getItemType(),
                entity.getManufacturer(),
                entity.getModelName(),
                entity.getModelNumber(),
                entity.getStatus(),
                entity.getRegDt(),
                entity.getUpdDt()
        );
    }

}