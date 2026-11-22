package com.smartrig.dto;

import com.smartrig.repository.entity.PcStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CompletePcCreateRequestDTO(

        @NotBlank(message = "PC 이름은 필수입니다.")
        @Size(max = 100, message = "PC 이름은 100자 이하로 입력해 주세요.")
        String name,

        @NotBlank(message = "스펙은 필수입니다.")
        String specs,

        @NotNull(message = "가격은 필수입니다.")
        @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
        Integer price,

        // 비워서 보내면 판매중(SALE)으로 등록된다.
        PcStatus status

) {
}