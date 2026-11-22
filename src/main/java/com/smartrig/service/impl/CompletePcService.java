package com.smartrig.service.impl;

import com.smartrig.dto.CompletePcResponseDTO;
import com.smartrig.repository.CompletePcRepository;
import com.smartrig.service.ICompletePcService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.smartrig.dto.CompletePcCreateRequestDTO;
import com.smartrig.repository.entity.CompletePcEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompletePcService implements ICompletePcService {

    private final CompletePcRepository completePcRepository;

    @Override
    public List<CompletePcResponseDTO> getPcList() {
        return completePcRepository.findAll(Sort.by(Sort.Direction.ASC, "pcId"))
                .stream()
                .map(CompletePcResponseDTO::from)
                .toList();
    }

    @Transactional
    @Override
    public CompletePcResponseDTO createPc(CompletePcCreateRequestDTO dto) {

        CompletePcEntity entity =
                new CompletePcEntity(dto.name(), dto.specs(), dto.price());

        // 상태를 골라서 보냈으면 그 값으로, 안 보냈으면 판매중(SALE) 그대로 둔다.
        if (dto.status() != null) {
            entity.changeStatus(dto.status());
        }

        CompletePcEntity saved = completePcRepository.save(entity);

        return CompletePcResponseDTO.from(saved);
    }
}