package com.smartrig.exception.handler;

import com.smartrig.exception.ModelNotFoundException;
import com.smartrig.exception.StockNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.smartrig.dto.ErrorResponseDTO;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // "모든 Controller에서 발생하는 예외를 내가 처리할게." 라고 Spring에게 알려주는 어노테이션이다.
public class GlobalExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class) // "ModelNotFoundException이 발생하면 이 메서드를 실행해."
    public ResponseEntity<ErrorResponseDTO> handleModelNotFoundException(ModelNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        null
                ));
    }

    // DB 제약조건 위반 예외 처리 (400 Bad Request)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "잘못된 요청입니다. 데이터 제약 조건을 위반했습니다.",
                        null
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        "입력값을 확인해주세요.",
                        errors
                ));
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleStockNotFoundException(
            StockNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        null
                ));
    }

}
