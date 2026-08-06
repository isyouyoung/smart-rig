package com.smartrig.service;

import com.smartrig.dto.CpuStockResponseDTO;
import com.smartrig.dto.ModelCreateRequestDTO;
import com.smartrig.dto.ModelResponseDTO;
import com.smartrig.dto.ModelUpdateRequestDTO;

import java.util.List;

// Model 관련 비즈니스 로직을 정의하는 Service 인터페이스이다.
public interface IModelService {

    // Model 생성 요청 DTO를 받아 DB에 저장한다.
    // 저장 완료 후 ResponseDTO 형태로 반환한다.
    ModelResponseDTO saveModel(ModelCreateRequestDTO dto);

    // Model 전체 목록 조회 반환타입 => List<ModelEntity>(여러 개)
    List<ModelResponseDTO> getModelList();

    List<ModelResponseDTO> getModelListByItemType(String itemType);

    // Model 목록중 하나 조회 반환타입 => ModelEntity(하나)
    ModelResponseDTO getModelByName(String modelName);

    // Model PK(modelId)로 특정 Model 하나를 조회한다.
    ModelResponseDTO getModelById(Long modelId);

    // modelId(PK)를 이용하여 특정 Model을 삭제한다.
    void deleteModelById(Long modelId);

    ModelResponseDTO updateModel(ModelUpdateRequestDTO dto);

    List<CpuStockResponseDTO> getCpuListWithStock();
}