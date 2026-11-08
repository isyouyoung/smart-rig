package com.smartrig.service;

import com.smartrig.dto.CompletePcResponseDTO;

import java.util.List;

public interface ICompletePcService {

    // 판매 PC 전체 목록 조회
    List<CompletePcResponseDTO> getPcList();
}