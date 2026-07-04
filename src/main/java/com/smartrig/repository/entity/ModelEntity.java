package com.smartrig.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

//    DB 컬럼 타입과 Entity 필드 타입은 가능한 한 1:1로 맞춘다.
//    BIGINT ↔ Long
//    INT ↔ Integer
//    VARCHAR ↔ String

@Entity // 이 클래스가 DB와 연결되는 Entity임을 JPA에게 알려준다.
@Table(name = "MODEL") // MODEL 테이블과 매핑한다.
public class ModelEntity {

    // ===== 필드(Field) =====
    // DB의 각 컬럼과 매핑되는 데이터를 저장한다.
    // 객체가 생성되면 각 필드에 값이 저장된다.
    // 아래의 private으로 선언된 변수들이 모두 필드(Field)이다.

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

    // DB의 model_number(VARCHAR(50) NOT NULL) 컬럼과 매핑된다.
    // 제품의 모델 번호(7500, 1060, 5600X 등)를 저장한다.
    // 사용자가 제품을 검색할 때 주로 사용되는 정보이다.
    // nullable = false : NULL 값을 허용하지 않는다.
    // length = 50 : 최대 50자까지 저장할 수 있다.
    @Column(name = "model_number", nullable = false, length = 50)
    private String modelNumber;

    // DB의 status(CHAR(1) NOT NULL DEFAULT 'Y') 컬럼과 매핑된다.
    // 제품의 사용 여부(Y: 사용, N: 미사용)를 저장한다.
    // nullable = false : NULL 값을 허용하지 않는다.
    // length = 1 : 한 글자만 저장할 수 있다.
    // DEFAULT 'Y'는 DB에서 값이 없을 경우 자동으로 적용된다.
    @Column(name = "status", nullable = false, length = 1)
    private String status;

    // DB의 reg_dt(DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP) 컬럼과 매핑된다.
    // 데이터가 처음 등록된 날짜와 시간을 저장한다.
    // nullable = false : NULL 값을 허용하지 않는다.
    // DEFAULT CURRENT_TIMESTAMP : DB가 저장 시 현재 시간을 자동으로 저장한다.
    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

    // DB의 upd_dt(DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP) 컬럼과 매핑된다.
    // 데이터가 마지막으로 수정된 날짜와 시간을 저장한다.
    // nullable = false : NULL 값을 허용하지 않는다.
    // DEFAULT CURRENT_TIMESTAMP : 최초 저장 시 현재 시간을 자동으로 저장한다.
    // ON UPDATE CURRENT_TIMESTAMP : 데이터 수정 시 현재 시간으로 자동 갱신된다.
    @Column(name = "upd_dt", nullable = false)
    private LocalDateTime updDt;

    // MODEL 테이블 Entity 매핑 완료!!!

    public Long getModelId() {
        return modelId;
    }

    public String getItemType() {
        return itemType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRegDt() {
        return regDt;
    }

    public LocalDateTime getUpdDt() {
        return updDt;
    }

    // modelName 값을 변경하는 Setter 메서드이다.
    // 매개변수로 전달받은 값을 현재 객체의 modelName 필드에 저장한다.
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegDt(LocalDateTime regDt) {
        this.regDt = regDt;
    }

    public void setUpdDt(LocalDateTime updDt) {
        this.updDt = updDt;
    }

}