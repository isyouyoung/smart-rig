package com.smartrig.controller;

import com.smartrig.dto.ModelCreateRequestDTO;
import com.smartrig.dto.ModelResponseDTO;
import com.smartrig.mapper.ModelMapper;
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
    public void saveModel(@RequestBody ModelCreateRequestDTO dto) {

        ModelEntity entity = ModelMapper.toEntity(dto);

        modelService.saveModel(entity);
        // Mapper를 통해 변환된 Entity를 Service 계층에 전달하여 저장 요청
    }

    // GET 방식으로 /model/v1/getModelList 주소 요청 시 실행된다.
    // => 보통 조회 화면 진입시 GET 요청을 보내 목록을 조회한다고 보면 됨
    // IModelService 설계도에 정의된 getModellist()를 호출하는대
    // 실제 실행되는 코드는 Override된 메서드임을 잊지말자~!
    // 조회한 ModelEntity 목록을 클라이언트에게 반환함
    // DTO로 변환 완료
    @GetMapping("/getModelList")
    public List<ModelResponseDTO> getModelList() {

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

    // PUT 방식으로 /model/v1/updateModel 주소 요청 시 실행된다.
    // 수정할 ModelEntity 정보를 전달받아 해당 Model 데이터를 수정한다.
    // 수정할 데이터는 JSON 형태로 RequestBody를 통해 전달받는다.
    // Service 계층에 수정을 요청한다.
    @PutMapping("/updateModel")
    public void updateModel(@RequestBody ModelEntity modelEntity) {
        modelService.updateModel(modelEntity);
    }

}

/*
 * Model API 테스트 완료
 *
     1. POST 저장 테스트
       - URL : /model/v1/saveModel
       - 결과 : 성공
       - 전달받은 ModelCreateRequestDTO 데이터를
         ModelMapper를 통해 ModelEntity로 변환 후
         MariaDB MODEL 테이블에 정상 저장 확인
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
 *    - URL : /model/v1/getModelById?modelId=1
 *    - 결과 : 성공
 *    - JpaRepository.findById()를 사용하여 PK 기준 조회 확인
 *    - Optional<ModelEntity>를 orElse(null)로 처리 후 정상 반환 확인
 *    - Controller → Service → Repository → Database 흐름 정상 동작 확인
 *
 * 6. DELETE 단건 삭제 테스트 (modelId PK)
 *    - URL : /model/v1/deleteModelById?modelId=1
 *    - DELETE 요청이 Controller → Service → Repository까지 정상 호출됨
 *    - JpaRepository.deleteById()가 정상 실행되는 것 확인
 *    - MODEL 테이블 삭제 시도 확인
 *    - STOCK 테이블에서 해당 model_id를 Foreign Key(FK)로 참조하고 있어
 *      DB에서 삭제를 제한하는 것 확인
 *    - 삭제 기능은 정상 구현되었으며, 삭제 실패 원인은
 *      DB의 Foreign Key 제약조건 때문임을 확인
 *
 * 2026-07-22 Model 저장, 조회, 삭제 API 테스트 완료
 */

/*
 *
 * 7. PUT 수정 테스트 (modelId PK)
 *    - URL : /model/v1/updateModel
 *    - 결과 : 성공
 *    - RequestBody를 통해 수정할 ModelEntity 데이터를 JSON 형태로 전달
 *    - Controller → Service → Repository → Database 흐름 정상 동작 확인
 *
 *    - 최초 테스트 시 문제 발생
 *      : Column 'upd_dt' cannot be null 오류 발생
 *      : ModelEntity의 updDt 값이 null인 상태에서 UPDATE를 수행하여
 *        MariaDB의 NOT NULL 제약조건에 의해 수정 실패
 *
 *    - 조치
 *      : 테스트 목적으로 JSON 요청 데이터에 regDt, updDt 값을 직접 전달
 *      : updDt 값이 정상적으로 Entity에 매핑되는 것을 확인
 *
 *    - 결과
 *      : Hibernate SELECT 후 UPDATE SQL 정상 실행 확인
 *      : MODEL 테이블 데이터 수정 확인
 *      : Postman 응답 정상 반환 확인
 *
 *    - 확인 내용
 *      : JpaRepository.save()를 통한 UPDATE 동작 방식 확인
 *      : JPA는 PK 값이 존재하는 Entity 저장 시 INSERT가 아닌 UPDATE 수행
 *      : 수정 시 Entity의 변경된 값을 감지하여 UPDATE Query 실행 확인
 *
 * 2026-07-25 Model UPDATE API 테스트 완료
 */