package com.smartrig.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "COMPLETE_PC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletePcEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private Long pcId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "specs", nullable = false, columnDefinition = "TEXT")
    private String specs;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PcStatus status;

    public CompletePcEntity(String name, String specs, Integer price) {
        this.name = name;
        this.specs = specs;
        this.price = price;
        this.status = PcStatus.SALE;
    }

    public void update(String name, String specs, Integer price) {
        this.name = name;
        this.specs = specs;
        this.price = price;
    }

    public void changeStatus(PcStatus status) {
        this.status = status;
    }

}