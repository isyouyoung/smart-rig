package com.smartrig.service.impl;

import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.service.IModelService;
import org.springframework.stereotype.Service;
import com.smartrig.repository.ModelRepository;
import com.smartrig.dto.ModelResponseDTO;
import com.smartrig.mapper.ModelMapper;

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

    // ModelEntity 저장 기능을 구현하는 메서드이다.
    // 실제 DB 저장은 Repository를 통해 수행한다.
    @Override
    public void saveModel(ModelEntity modelEntity) {
        modelRepository.save(modelEntity);
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
    public ModelEntity getModelByName(String modelName) {
        return modelRepository.findByModelName(modelName);
    }

    @Override
    public ModelResponseDTO getModelById(Long modelId) {

        ModelEntity entity = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("해당 모델이 없습니다."));

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

    // ModelEntity 정보를 수정하는 기능을 구현하는 메서드이다.
    // JpaRepository가 기본으로 제공하는 save()를 사용하여 DB 데이터를 수정한다.
    // save()는 modelId(PK)가 존재하면 새로운 데이터를 추가하지 않고
    // 기존 데이터를 UPDATE 한다.
    // 따라서 수정할 데이터가 담긴 ModelEntity 객체를 전달받아 수정한다.
    // 추가 설명
    // JpaRepository의 save()는 저장(INSERT)과 수정(UPDATE)을 모두 담당하는 메서드이다.
    //
    // ModelEntity의 modelId(PK)가 없으면 새로운 데이터로 판단하여 INSERT를 수행한다.
    // ModelEntity의 modelId(PK)가 이미 존재하면 기존 데이터를 찾아 UPDATE를 수행한다.
    //
    // 즉,
    // modelId == null        → INSERT
    // modelId가 존재함      → UPDATE
    //
    // 최종적으로 Service에서는 전달받은 ModelEntity를 저장하거나 수정하도록
    // Repository에게 요청한다.
    @Override
    public void updateModel(ModelEntity modelEntity) {
        modelRepository.save(modelEntity);
    }

}