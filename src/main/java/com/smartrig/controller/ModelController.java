package com.smartrig.controller;

import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.service.IModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Model 관련 요청을 처리하는 Controller이다.
// 클라이언트의 요청을 받아 Service 계층으로 전달한다.
@RestController
@RequestMapping(value = "/model/v1")
public class ModelController {

    // 비즈니스 로직 처리를 위해 Service를 주입받는다.
    private final IModelService modelService;

    // 생성자 주입(Constructor Injection)을 사용한다.
    public ModelController(IModelService modelService) {
        this.modelService = modelService;
    }

//    @PostMapping("/saveModel")
//    public void saveModel() {
//    }

    // Model 저장 요청을 처리하는 API이다.
    @PostMapping("/saveModel")
    public void saveModel(ModelEntity modelEntity) {
        modelService.saveModel(modelEntity);
    }

}