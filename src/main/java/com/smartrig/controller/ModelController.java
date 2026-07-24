package com.smartrig.controller;

import com.smartrig.repository.entity.ModelEntity;
import com.smartrig.service.IModelService;
import org.springframework.web.bind.annotation.*;

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

    // GET 방식으로 /model/v1/getModelById 주소 요청 시 실행된다.
    // modelId(PK)를 전달받아 해당 Model 하나를 조회한다.
    // PK는 중복되지 않으므로 항상 하나의 데이터만 조회된다.
    // 존재하지 않는 경우 null을 반환한다.
    // 조회 결과를 클라이언트에게 반환한다.
    @GetMapping("/getModelById")
    public ModelEntity getModelById(@RequestParam Long modelId) {
        return modelService.getModelById(modelId);
    }

    // DELETE 방식으로 /model/v1/deleteModelById 주소 요청 시 실행된다.
    // modelId(PK)를 전달받아 해당 Model 하나를 삭제한다.
    // PK는 중복되지 않으므로 하나의 데이터만 삭제된다.
    @DeleteMapping("/deleteModelById")
    public void deleteModelById(@RequestParam Long modelId) {
        modelService.deleteModelById(modelId);
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
 * 3. GET 단건 조회 테스트 (modelName)
 *    - URL : /model/v1/getModelByName?modelName=i5-7500
 *    - 최초 결과 : 500 Internal Server Error 발생
 *    - 원인 : 동일한 modelName 데이터가 2건 존재하여
 *             JpaRepository.findByModelName() 조회 시
 *             NonUniqueResultException 발생
 *    - 조치 : 중복 데이터를 삭제 후 재조회
 *    - 결과 : 정상적으로 ModelEntity 1건 조회 확인
 *
 * 4. 확인 내용
 *    - Controller → Service → Repository → Database 흐름 정상 동작 확인
 *    - JpaRepository.findAll()을 통한 전체 조회 정상 동작 확인
 *    - JpaRepository.findByModelName() 동작 방식 이해
 *    - 중복 데이터 조회 시 NonUniqueResultException 발생 원인 확인
 *    - POST 저장 후 생성된 model_id 값 정상 증가 확인
 *
 * 5. GET 단건 조회 테스트 (modelId PK)
       - URL : /model/v1/getModelById?modelId=1
       - 결과 : 성공
       - JpaRepository.findById()를 사용하여 PK 기준 조회 확인
       - Optional<ModelEntity)를 orElse(null)로 처리 후 정상 반환 확인
       - Controller → Service → Repository → Database 흐름 정상 동작 확인
 *
 * 2026-07-22 Model 저장 및 조회 API 테스트 완료
 */