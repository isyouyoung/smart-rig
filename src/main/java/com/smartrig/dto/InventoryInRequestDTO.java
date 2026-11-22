package com.smartrig.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record InventoryInRequestDTO(

        @NotNull(message = "입고할 부품(modelId)은 필수입니다.")
        Long modelId,

        @NotNull(message = "입고 수량은 필수입니다.")
        @Positive(message = "입고 수량은 1개 이상이어야 합니다.")
        Integer quantity,

        // 개당 매입가. 모르면 비워도 된다.
        @PositiveOrZero(message = "매입가는 0원 이상이어야 합니다.")
        Long unitPrice,

        // 구매일. 모르면 비워도 된다.
        LocalDate purchaseDate,

        @Size(max = 500, message = "메모는 500자 이하로 입력해 주세요.")
        String memo

) {
}