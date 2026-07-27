package com.smartrig.dto;

import jakarta.validation.constraints.NotBlank;

// 데이터 전달만을 위한 DTO 생성
// 요청과 응답을 분리해서 생성함
// Request DTO = "사용자가 보내는 데이터"
// record 타입으로 객체에 담긴 데이터 수정 불가
// record 타입은 () 안에 필드를 선언한다
public record ModelCreateRequestDTO(

        @NotBlank(message = "부품 종류는 필수입니다.")
        String itemType,

        @NotBlank(message = "제조사는 필수입니다.")
        String manufacturer,

        @NotBlank(message = "모델명은 필수입니다.")
        String modelName,

        @NotBlank(message = "모델 번호는 필수입니다.")
        String modelNumber

) {

}
