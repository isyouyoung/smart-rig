package com.smartrig.repository;

import com.smartrig.repository.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository는 DB와 직접 통신하는 계층이다.
// Service는 Repository를 통해 DB에 접근한다.
// Entity를 저장, 조회, 수정, 삭제하는 역할을 담당한다.
// Spring Data JPA의 JpaRepository를 상속받아 기본 CRUD 기능을 자동으로 사용할 수 있다.
// ModelEntity를 관리하며, 기본키(PK) 타입은 Long이다.
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {

    ModelEntity findByModelName(String modelName);

}