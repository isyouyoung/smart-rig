package com.smartrig.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

// DB 컬럼 타입과 Entity 필드 타입은 가능한 한 1:1로 맞춘다.
// BIGINT ↔ Long
// VARCHAR ↔ String

@Builder // 편의성 (객체를 만들때 값을 알아보기 쉽게 넣도록 도와줌 Lombok 기능)
@AllArgsConstructor // 쪼개면 쉬움 (전부 + 인자 + 생성자 // 모든 필드를 매개변수로 받는 생성자 자동 생성)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 만들어줌 (위에 all이랑 세트로 보통사용)
@Entity // 이 클래스를 JPA Entity로 관리해라라고 명시해주는것
@Table(name = "BOARD_DETAIL") // 이 엔티티를 DB의 BOARD_DETAIL 테이블과 연결해서 사용해라
public class BoardDetailEntity extends BaseEntity {

    @Id // 이 필드를 이 Entity의 PK로 사용해라 (식별자)
    @Column(name = "model_id") // Java의 modelId 필드를 DB의 model_id 컬럼과 연결해라
    private Long modelId;

    // Java의 manufacturer 필드를 DB의 manufacturer 컬럼과 연결
    // DB에서 이 컬럼을 NULL로 비워두지 못하게 해라
    // 최대 50자 문자열을 저장
    @Column(name = "manufacturer", nullable = false, length = 50)
    private String manufacturer;

    // Java의 generation 필드와 DB의 generation 컬럼을 매핑
    // 메인보드 상세정보에서는 generation이 반드시 있어야 하므로 NULL 허용X
    // 최대 20자 문자열을 저장
    @Column(name = "generation", nullable = false, length = 20)
    private String generation;

    // Java의 company 필드와 DB의 company 컬럼을 매핑
    // 메인보드는 각 제작사 ASUS, MSI, GIGABYTE 등 회사가 있으므로 NULL 허용X
    // 최대 50자 문자열을 저장
    @Column(name = "company", nullable = false, length = 50)
    private String company;

    // Java의 socket 필드와 DB의 socket 컬럼을 매핑
    // 추후에 CPU에도 소켓 정보 추가후 매핑(연동) 예정
    // 메인보드는 각 소켓 번호가 있으므로 NULL 허용X
    // 최대 30자 문자열을 저장
    @Column(name = "socket", nullable = false, length = 30)
    private String socket;

    // Java의 chipsetSeries 필드와 DB의 chipset_series 컬럼을 매핑
    // 메인보드는 H, B, Z 등 등급이 나뉘어져 있으므로 NULL 허용X
    // 최대 20자 문자열을 저장
    @Column(name = "chipset_series", nullable = false, length = 20)
    private String chipsetSeries;

    // Java의 chipset 필드와 DB의 chipset 컬럼을 매핑
    // 메인보드의 세부 칩셋명 H81, B85, Z97 등을 저장
    // 최대 30자 문자열을 저장
    @Column(name = "chipset", nullable = false, length = 30)
    private String chipset;

}
