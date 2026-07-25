package com.smartrig.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass

// JPA가 Entity 저장/수정 시 자동으로 감지한다.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {


    // 최초 생성 시간
    // UPDATE 시 변경하지 않는다.
    @CreatedDate
    @Column(name = "reg_dt", updatable = false)
    private LocalDateTime regDt;


    // 마지막 수정 시간
    // UPDATE 발생 시 자동 변경된다.
    @LastModifiedDate
    @Column(name = "upd_dt")
    private LocalDateTime updDt;

}