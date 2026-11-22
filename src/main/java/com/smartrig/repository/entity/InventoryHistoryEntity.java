package com.smartrig.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "INVENTORY_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "model_id", nullable = false)
    private Long modelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false, length = 3)
    private InventoryChangeType changeType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price")
    private Long unitPrice;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "source_text", columnDefinition = "TEXT")
    private String sourceText;

    @Column(name = "memo", columnDefinition = "TEXT")
    private String memo;

    @Column(name = "reg_dt", nullable = false, updatable = false)
    private LocalDateTime regDt;

    // 입고 기록을 만든다.
    public static InventoryHistoryEntity ofIn(Long modelId, Integer quantity,
                                              Long unitPrice, LocalDate purchaseDate,
                                              String memo) {
        InventoryHistoryEntity entity = new InventoryHistoryEntity();
        entity.modelId = modelId;
        entity.changeType = InventoryChangeType.IN;
        entity.quantity = quantity;
        entity.unitPrice = unitPrice;
        entity.purchaseDate = purchaseDate;
        entity.memo = memo;
        entity.regDt = LocalDateTime.now();
        return entity;
    }
}