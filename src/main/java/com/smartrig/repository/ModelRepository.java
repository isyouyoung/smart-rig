package com.smartrig.repository;

import com.smartrig.repository.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository는 DB와 직접 통신하는 계층이다.
// Service는 Repository를 통해 DB에 접근한다.
// Entity를 저장, 조회, 수정, 삭제하는 역할을 담당한다.
// Spring Data JPA의 JpaRepository를 상속받아 기본 CRUD 기능을 자동으로 사용할 수 있다.
// ModelEntity를 관리하며, 기본키(PK) 타입은 Long이다.
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {

    // modelName으로 MODEL 테이블을 조회한다.
    // 메서드 이름을 기반으로 Spring Data JPA가 SELECT 쿼리를 자동 생성한다.
    ModelEntity findByModelName(String modelName);

}