package com.smartrig.controller;

import jakarta.validation.Valid;
import com.smartrig.auth.UserRole;
import com.smartrig.controller.response.CommonResponse;
import com.smartrig.dto.MsgDTO;
import com.smartrig.dto.UserInfoDTO;
import com.smartrig.service.IUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/reg/v1")
@RequiredArgsConstructor
@RestController
public class UserRegController {

    private final IUserInfoService userInfoService;

    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "getUserIdExists")
    public ResponseEntity<CommonResponse<UserInfoDTO>> getUserIdExists(@RequestBody UserInfoDTO pDTO)
        throws Exception {

        log.info("{}.getUserIdExists Start!", getClass().getName());

        UserInfoDTO rDto = userInfoService.getUserIdExists(pDTO);

        log.info("{}.getUserIdExists End!", getClass().getName());

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rDto));
    }

    @PostMapping(value = "insertUserInfo")
    public ResponseEntity<?> insertUserInfo(@Valid @RequestBody UserInfoDTO pDTO, BindingResult bindingResult) {

        log.info("{}.insertUserInfo Start!", getClass().getName());

        // 1. 유효성 검증 실패 시 에러 메시지 반환
        if (bindingResult.hasErrors()) {
            return CommonResponse.getErrors(bindingResult);
        }

        int res = 0; // 회원가입 처리 결과 코드
        String msg = ""; // 처리 결과 메시지
        MsgDTO dto; // 응답 메시지 객체

        log.info("pDTO : {}", pDTO);

        try {
            // 2. 전달받은 회원 정보에 비밀번호 암호화 및 권한 추가
            UserInfoDTO nDTO = UserInfoDTO.createUser(
                    pDTO,
                    bCryptPasswordEncoder.encode(pDTO.password()),
                    UserRole.USER.getValue()
            );

            // 3. 회원가입 처리
            res = userInfoService.insertUserInfo(nDTO);

            log.info("회원가입 결과(res) : {}", res);

            // 4. 처리 결과 메시지 구성
            if (res == 1) {
                msg = "회원가입 되었습니다.";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e;
            res = 2;
            log.error("회원가입 중 예외 발생", e);

        } finally {
            dto = MsgDTO.builder().result(res).msg(msg).build();
            log.info("{}.insertUserInfo End!", getClass().getName());
        }

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), dto));
    }

}
