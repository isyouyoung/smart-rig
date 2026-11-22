package com.smartrig.controller;

import com.smartrig.dto.CompletePcResponseDTO;
import com.smartrig.service.ICompletePcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smartrig.dto.CompletePcCreateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pcs")
public class CompletePcController {

    private final ICompletePcService completePcService;

    @GetMapping
    public ResponseEntity<List<CompletePcResponseDTO>> getPcList() {
        return ResponseEntity.ok(completePcService.getPcList());
    }

    @PostMapping
    public ResponseEntity<CompletePcResponseDTO> createPc(
            @Valid @RequestBody CompletePcCreateRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(completePcService.createPc(dto));
    }
}