package com.smartrig.service.impl;

import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.service.IModelService;
import org.springframework.stereotype.Service;
import com.smartrig.repository.ModelRepository;

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
    public List<ModelEntity> getModelList() {
        return modelRepository.findAll();
    }

    // modelName으로 특정 Model을 조회하는 기능을 구현하는 메서드이다.
    // 실제 DB 조회는 Repository의 findByModelName()을 통해 수행한다.
    @Override
    public ModelEntity getModelByName(String modelName) {
        return modelRepository.findByModelName(modelName);
    }

    // modelId(PK)를 이용하여 특정 Model 조회 기능을 구현하는 메서드이다.
    // JpaRepository가 기본으로 제공하는 findById()를 사용하여 DB에서 PK 기준으로 조회한다.
    // findById()는 Optional<ModelEntity> 형태로 반환하기 때문에
    // orElse(null)을 사용하여 실제 ModelEntity 객체를 꺼낸다.
    // 만약 조회 결과가 없으면 null을 반환한다.

    // 추가 설명
    // Optional은 조회 결과가 있을 수도 있고 없을 수도 있는 상황을 표현하는 객체이다.
    // 예를 들어 modelId가 존재하면 Optional 안에 ModelEntity가 들어있고,
    // 존재하지 않으면 비어있는 Optional 객체가 반환된다.
    //
    // orElse(null)은 Optional 안의 데이터를 꺼내는 역할을 한다.
    // 조회 결과가 존재하면 ModelEntity를 반환하고,
    // 조회 결과가 없으면 null을 반환한다.
    //
    // 즉,
    // findById(modelId) → Optional<ModelEntity> 반환
    // orElse(null)      → Optional 안의 ModelEntity 추출
    //
    // 최종적으로 Service에서는 ModelEntity 형태로 Controller에게 전달한다.
    @Override
    public ModelEntity getModelById(Long modelId) {
        return modelRepository.findById(modelId).orElse(null);
    }

    // modelId(PK)를 이용하여 특정 Model을 삭제하는 기능을 구현하는 메서드이다.
    // JpaRepository가 기본으로 제공하는 deleteById()를 사용하여
    // PK(modelId)를 기준으로 DB의 데이터를 삭제한다.
    // deleteById()는 삭제만 수행하므로 반환값이 없다.
    @Override
    public void deleteModelById(Long modelId) {
        modelRepository.deleteById(modelId);
    }

}