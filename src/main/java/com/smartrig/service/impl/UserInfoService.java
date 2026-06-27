package com.smartrig.service.impl;

import com.smartrig.auth.AuthInfo;
import com.smartrig.dto.UserInfoDTO;
import com.smartrig.repository.UserInfoRepository;
import com.smartrig.repository.entity.UserInfoEntity;
import com.smartrig.service.IUserInfoService;
import com.smartrig.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Transactional(readOnly = true)
    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) {

        return userInfoRepository.findByUserId(pDTO.userId())
                .map(e -> UserInfoDTO.builder()
                        .existsYn("Y")
                        .build())

                .orElseGet(() -> UserInfoDTO.builder()
                        .existsYn("N")
                        .build());
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("{}.loadUserByUsername Start!", this.getClass().getName());

        log.info("userId : {}", username);

        UserInfoEntity rEntity = userInfoRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " Not Found User"));

        UserInfoDTO rDTO = UserInfoDTO.from(rEntity);

        return new AuthInfo(rDTO);
    }

    // 회원가입 (1)

    @Override
    @Transactional
    public int insertUserInfo(UserInfoDTO pDTO) {

        log.info("{}.insertUserInfo Start!", this.getClass().getName());

        int res;

        log.info("pDTO : {}", pDTO);

        try {

            // 1. 회원 아이디 중복 여부 확인
            boolean exists = userInfoRepository.existsById(pDTO.userId());

            if (exists) {
                // 이미 같은 아이디가 존재 -> 중복 가입 방지
                res =2;

            } else {
                // 2. DTO -> Entity 변환
                UserInfoEntity pEntity = UserInfoDTO.of(pDTO);

                // 3. DB 저장
                userInfoRepository.save(pEntity);

                res = 1;
            }
        }   catch (Exception e) {
            log.error("insertUserInfo error", e);
            res = 0; // 예외 발생 시 0 반환
        }

        log.info("{}.insertUserInfo End! res={}", this.getClass().getName(), res);

        return res;
    }

    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info("{}.getUserInfo Start!", this.getClass().getName());

        //회원 아이디
        String user_id = CmmUtil.nvl(pDTO.userId());

        log.info("user_id : {}", user_id);

        // SELECT * FROM USER_INFO WHERE USER_ID = "hglee67" 쿼리 실행과 동일
        UserInfoDTO rDTO = UserInfoDTO.from(userInfoRepository.findByUserId(user_id).orElseThrow());

        log.info("{}.getUserInfo End!", this.getClass().getName());

        return rDTO;
    }



}
