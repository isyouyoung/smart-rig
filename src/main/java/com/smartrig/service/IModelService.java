package com.smartrig.service;

import com.smartrig.repository.entity.ModelEntity;

import java.util.List;

// Model 관련 비즈니스 로직을 정의하는 Service 인터페이스이다.
public interface IModelService {

    // ModelEntity 객체를 저장하기 위한 메서드이다.
    // 실제 저장 로직은 구현 클래스(ModelService)에서 작성한다.
    void saveModel(ModelEntity modelEntity);

    // Model 전체 목록 조회 반환타입 => List<ModelEntity>(여러 개)
    List<ModelEntity> getModelList();

    // Model 목록중 하나 조회 반환타입 => ModelEntity(하나)
    ModelEntity getModelByName(String modelName);
}