package com.smartrig.mapper;

import com.smartrig.dto.ModelCreateRequestDTO;
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

}