package com.smartrig.controller;

import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.service.IModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    
    // Model 저장 요청을 처리하는 API이다.
    @PostMapping("/saveModel")
    // 위 코드에 RequestMapping을 기본주소에 추가로 /model/v1/saveModel 가 됨
    public void saveModel(@RequestBody ModelEntity modelEntity) {
        // 클라이언트로부터 modelEntity 객체를 매개변수로 받겠다는 뜻

        System.out.println(modelEntity);

        modelService.saveModel(modelEntity);
        // 전달받은 modelEntity를 Service한태 저장하라고 요청
    }

    // GET 방식으로 /model/v1/getModelList 주소 요청 시 실행된다.
    // => 보통 조회 화면 진입시 GET 요청을 보내 목록을 조회한다고 보면 됨
    // IModelService 설계도에 정의된 getModellist()를 호출하는대
    // 실제 실행되는 코드는 Override된 메서드임을 잊지말자~!
    // 조회한 ModelEntity 목록을 클라이언트에게 반환함
    @GetMapping("/getModelList")
    public List<ModelEntity> getModelList() {
        return modelService.getModelList();
    }

    // GET 방식으로 /model/v1/getModelByName 주소 요청 시 실행된다.
    // modelName을 전달받아 해당 Model 하나를 조회한다.
    // 조회 결과를 클라이언트에게 반환한다.
    // @RequestParam : URL의 파라미터 값을 매개변수에 자동으로 매핑한다.
    @GetMapping("/getModelByName")
    public ModelEntity getModelByName(@RequestParam String modelName) {
        return modelService.getModelByName(modelName);
    }

}

/*
 * Model API 테스트 완료
 *
 * 1. POST 저장 테스트
 *    - URL : /model/v1/saveModel
 *    - 결과 : 성공
 *    - 전달받은 ModelEntity 데이터가 MariaDB MODEL 테이블에 정상 저장됨
 *
 * 2. GET 전체 조회 테스트
 *    - URL : /model/v1/getModelList
 *    - 결과 : 성공
 *    - DB에 저장된 MODEL 데이터를 Entity로 조회 후 JSON 응답 확인 완료
 *
 * 3. 확인 내용
 *    - Controller → Service → Repository → Database 흐름 정상 동작 확인
 *    - JpaRepository.findAll()을 통한 전체 조회 정상 동작 확인
 *    - POST 저장 후 생성된 model_id 값 정상 증가 확인
 *
 * 2026-07-21 Model 저장 및 조회 API 테스트 완료
 */