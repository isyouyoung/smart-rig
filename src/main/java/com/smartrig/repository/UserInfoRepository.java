package com.smartrig.repository;

import com.smartrig.repository.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {

    // 회원 정보 조회(회원정보 화면, 회원가입 여부 등 다양하게 활용됨)
    Optional<UserInfoEntity> findByUserId(String userId);

}
