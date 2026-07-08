package com.smartrig.service.impl;

import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.service.IModelService;
import org.springframework.stereotype.Service;

// IModelService를 구현하는 Service 클래스이다.
// 실제 비즈니스 로직을 작성하는 계층이다.
@Service
public class ModelService implements IModelService {

    // ModelEntity 저장 기능을 구현하는 메서드이다.
    // 실제 DB 저장은 Repository를 통해 수행한다.
    @Override
    public void saveModel(ModelEntity modelEntity) {

    }

}