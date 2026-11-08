package com.smartrig.controller;

import com.smartrig.dto.CompletePcResponseDTO;
import com.smartrig.service.ICompletePcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}