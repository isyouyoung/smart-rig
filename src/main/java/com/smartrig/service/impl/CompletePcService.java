package com.smartrig.service.impl;

import com.smartrig.dto.CompletePcResponseDTO;
import com.smartrig.repository.CompletePcRepository;
import com.smartrig.service.ICompletePcService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompletePcService implements ICompletePcService {

    private final CompletePcRepository completePcRepository;

    @Override
    public List<CompletePcResponseDTO> getPcList() {
        return completePcRepository.findAll(Sort.by(Sort.Direction.DESC, "pcId"))
                .stream()
                .map(CompletePcResponseDTO::from)
                .toList();
    }
}