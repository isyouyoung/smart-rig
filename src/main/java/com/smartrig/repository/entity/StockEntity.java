package com.smartrig.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "STOCK")
public class StockEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "model_id", nullable = false, unique = true)
    private Long modelId;

    @Builder.Default
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    // ===== Getter =====

    public Long getStockId() {
        return stockId;
    }

    public Long getModelId() {
        return modelId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // ===== 비즈니스 / 도메인 메서드 =====

    /**
     * 재고 수량 직접 변경 (재고 조사/강제 조정용)
     */
    public void updateQuantity(Integer newQuantity) {
        if (newQuantity == null || newQuantity < 0) {
            throw new IllegalArgumentException("재고 수량은 0개 이상이어야 합니다.");
        }
        this.quantity = newQuantity;
    }

    /**
     * 재고 입고 처리 (+)
     */
    public void increase(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("입고 수량은 1개 이상이어야 합니다.");
        }
        this.quantity += amount;
    }

    /**
     * 재고 출고 처리 (-)
     */
    public void decrease(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("출고 수량은 1개 이상이어야 합니다.");
        }
        if (this.quantity < amount) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.quantity -= amount;
    }
}