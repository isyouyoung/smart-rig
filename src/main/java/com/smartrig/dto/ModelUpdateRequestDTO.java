package com.smartrig.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModelUpdateRequestDTO(

        @NotNull(message = "수정할 모델의 ID는 필수입니다.")
        Long modelId,

        @NotBlank(message = "부품 종류는 필수입니다.")
        String itemType,

        @NotBlank(message = "제조사는 필수입니다.")
        String manufacturer,

        String series,

        String generation,

        @NotBlank(message = "모델명은 필수입니다.")
        String modelName,

        @NotBlank(message = "모델 번호는 필수입니다.")
        String modelNumber,

        @NotBlank(message = "상태 값은 필수입니다.")
        String status

) {
}