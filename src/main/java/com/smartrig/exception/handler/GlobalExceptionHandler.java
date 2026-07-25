package com.smartrig.exception.handler;

import com.smartrig.exception.ModelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // "모든 Controller에서 발생하는 예외를 내가 처리할게." 라고 Spring에게 알려주는 어노테이션이다.
public class GlobalExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class) // "ModelNotFoundException이 발생하면 이 메서드를 실행해."
    public ResponseEntity<String> handleModelNotFoundException(ModelNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

}
