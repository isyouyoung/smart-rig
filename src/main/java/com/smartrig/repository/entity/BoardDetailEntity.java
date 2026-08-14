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

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "BOARD_DETAIL")
public class BoardDetailEntity extends BaseEntity {

    @Id
    @Column(name = "model_id")
    private Long modelId;

}
