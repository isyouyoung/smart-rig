package com.smartrig.service.impl;

import com.smartrig.dto.ModelCreateRequestDTO;
import com.smartrig.dto.ModelUpdateRequestDTO;
import com.smartrig.exception.ModelNotFoundException;
import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.service.IModelService;
import org.springframework.stereotype.Service;
import com.smartrig.repository.ModelRepository;
import com.smartrig.dto.ModelResponseDTO;
import com.smartrig.mapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// IModelService를 구현하는 Service 클래스이다.
// 실제 비즈니스 로직을 작성하는 계층이다.
@Service
public class ModelService implements IModelService {

    // DB 접근을 위해 ModelRepository를 주입받는다.
    private final ModelRepository modelRepository;

    // 생성자를 통해 ModelRepository를 주입받는다.
    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    // Model 생성 기능을 구현하는 메서드이다.
    // Controller에서 전달받은 CreateDTO를 Entity로 변환 후 저장한다.
    // 저장된 Entity는 ResponseDTO로 변환하여 반환한다.
    @Transactional
    @Override
    public ModelResponseDTO saveModel(ModelCreateRequestDTO dto) {
        // 1. DTO -> Entity 변환
        ModelEntity entity = ModelMapper.toEntity(dto);

        // 2. DB 저장 (저장된 객체에는 PK, 생성일자 등이 포함되어 돌아옴)
        ModelEntity savedEntity = modelRepository.save(entity);

        // 3. Entity -> DTO 변환 후 반환
        return ModelMapper.toDTO(savedEntity);
    }

    // Model 목록 조회 기능을 구현하는 메서드이다.
    // 현재는 구현 전 단계이므로 null을 반환한다.
    @Override
    public List<ModelResponseDTO> getModelList() {

        return modelRepository.findAll()
                .stream()
                .map(ModelMapper::toDTO)
                .toList();
    }

    // modelName으로 특정 Model을 조회하는 기능을 구현하는 메서드이다.
    // 실제 DB 조회는 Repository의 findByModelName()을 통해 수행한다.
    @Override
    public ModelResponseDTO getModelByName(String modelName) {

        ModelEntity entity = modelRepository.findByModelName(modelName)
                .orElseThrow(() -> new ModelNotFoundException("해당 모델이 없습니다."));

        return ModelMapper.toDTO(entity);
    }

    @Override
    public ModelResponseDTO getModelById(Long modelId) {

        ModelEntity entity = modelRepository.findById(modelId)
                .orElseThrow(() -> new ModelNotFoundException("해당 모델이 없습니다."));

        return ModelMapper.toDTO(entity);
    }

    // modelId(PK)를 이용하여 특정 Model을 삭제하는 기능을 구현하는 메서드이다.
    // JpaRepository가 기본으로 제공하는 deleteById()를 사용하여
    // DB에서 PK(modelId)를 기준으로 해당 데이터를 삭제한다.
    // deleteById()는 삭제만 수행하므로 반환값이 없다.
    @Override
    public void deleteModelById(Long modelId) {
        modelRepository.deleteById(modelId);
    }

    @Transactional
    @Override
    public ModelResponseDTO updateModel(ModelUpdateRequestDTO requestDTO) {

        ModelEntity entity = modelRepository.findById(requestDTO.modelId())
                .orElseThrow(() ->
                        new ModelNotFoundException("수정할 모델이 존재하지 않습니다. ID: " + requestDTO.modelId()
                        )
                );

        entity.update(
                requestDTO.itemType(),
                requestDTO.manufacturer(),
                requestDTO.modelName(),
                requestDTO.modelNumber(),
                requestDTO.status()
        );

        return ModelMapper.toDTO(entity);
    }

}