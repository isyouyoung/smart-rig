package com.smartrig.repository;

import com.smartrig.repository.entity.InventoryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryHistoryRepository extends JpaRepository<InventoryHistoryEntity, Long> {

    // 최근 입출고 10건 (대시보드의 "최근 입출고"에서 쓸 예정)
    List<InventoryHistoryEntity> findTop10ByOrderByHistoryIdDesc();
}