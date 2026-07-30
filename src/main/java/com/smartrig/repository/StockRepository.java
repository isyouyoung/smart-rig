package com.smartrig.repository;

import com.smartrig.repository.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

    // 특정 model_id의 재고 엔티티 조회
    Optional<StockEntity> findByModelId(Long modelId);

}