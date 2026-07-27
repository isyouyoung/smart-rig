package com.smartrig.dto;

import java.util.Map;

public record ErrorResponseDTO(

        int code,
        String message,
        Map<String, String> errors


) {
}
