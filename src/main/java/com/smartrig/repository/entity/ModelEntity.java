package com.smartrig.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

//    DB 컬럼 타입과 Entity 필드 타입은 가능한 한 1:1로 맞춘다.
//    BIGINT ↔ Long
//    INT ↔ Integer
//    VARCHAR ↔ String

@Entity // 이 클래스가 DB와 연결되는 Entity임을 JPA에게 알려준다.
@Table(name = "MODEL") // MODEL 테이블과 매핑한다.
public class ModelEntity {
    // MODEL 테이블의 PRIMARY KEY(model_id)와 연결된다.
    // JPA는 이 값을 기준으로 어떤 데이터를 조회/수정/삭제할지 판단한다.
    // DB의 BIGINT 타입과 매핑된다.
    // Long을 사용하는 이유는 저장 전에는 ID가 없어 null 상태가 될 수 있기 때문이다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ID는 개발자가 직접 넣지 않는다.
    // save()가 호출되면 DB(MariaDB)의 AUTO_INCREMENT가 번호를 생성하고,
    // JPA가 생성된 번호를 Entity의 modelId에 자동으로 채워준다.
    private Long modelId;

    // DB의 item_type(VARCHAR(10) NOT NULL) 컬럼과 매핑된다.
    // nullable = false : NULL 값을 허용하지 않는다.
    // length = 10 : 최대 10자까지 저장할 수 있다.
    @Column(
            name = "item_type",
            nullable = false,
            length = 10
    )
    private String itemType;

    // DB의 manufacturer(VARCHAR(50) NOT NULL) 컬럼과 매핑된다.
    // 제조사(Intel, AMD, NVIDIA 등)를 저장한다.
    // nullable = false : NULL 값을 허용하지 않는다.
    // length = 50 : 최대 50자까지 저장할 수 있다.
    @Column(name = "manufacturer", nullable = false, length = 50)
    private String manufacturer;

    // DB의 model_name(VARCHAR(100) NOT NULL) 컬럼과 매핑된다.
    // 제품의 모델명(i5-7500, GTX1060 6GB 등)을 저장한다.
    // nullable = false : NULL 값을 허용하지 않는다.
    // length = 100 : 최대 100자까지 저장할 수 있다.
    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

    // 다음 작성할거
    // model_number VARCHAR(50) NOT NULL


}