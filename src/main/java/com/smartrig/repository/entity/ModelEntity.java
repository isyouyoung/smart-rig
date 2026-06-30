package com.smartrig.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // 이 클래스가 DB와 연결되는 Entity임을 JPA에게 알려준다.
@Table(name = "MODEL") // MODEL 테이블과 매핑한다.
public class ModelEntity {

    // MODEL 테이블의 PRIMARY KEY(model_id)와 연결된다.
    // JPA는 이 값을 기준으로 어떤 데이터를 조회/수정/삭제할지 판단한다.
    @Id
    private Long modelId;

}