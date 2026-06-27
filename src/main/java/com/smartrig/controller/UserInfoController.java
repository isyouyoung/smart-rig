package com.smartrig.controller;

import com.smartrig.controller.response.CommonResponse;
import com.smartrig.dto.UserInfoDTO;
import com.smartrig.service.IUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequestMapping(value = "/user/v1")
@RequiredArgsConstructor
@RestController
public class UserInfoController {

    private final IUserInfoService userInfoService;

    @PostMapping("userInfo")
    public ResponseEntity<CommonResponse<UserInfoDTO>> userInfo(@AuthenticationPrincipal Jwt jwt) throws Exception {
        log.info("{}.userInfo Start!", getClass().getName());

        if (jwt == null) {
            log.warn("JWT principal is null - unauthorized access to /user/v1/userInfo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(CommonResponse.of(
                            HttpStatus.UNAUTHORIZED,
                            HttpStatus.UNAUTHORIZED.series().name(),
                            UserInfoDTO.builder().build()));
        }

        final String userId = jwt.getSubject();

        UserInfoDTO pDTO = UserInfoDTO.builder().userId(userId).build();

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserInfo(pDTO))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info("{}.userInfo End!", getClass().getName());

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rDTO)
        );
    }
}
