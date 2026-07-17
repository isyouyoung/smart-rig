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

}