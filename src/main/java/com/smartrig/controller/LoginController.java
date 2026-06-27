package com.smartrig.controller;

import jakarta.servlet.http.HttpServletResponse;
import com.smartrig.auth.AuthInfo;
import com.smartrig.controller.response.CommonResponse;
import com.smartrig.dto.MsgDTO;
import com.smartrig.dto.UserInfoDTO;
import com.smartrig.service.IJwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping(value = "/login/v1")
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final IJwtTokenService jwtTokenService;

    @PostMapping("loginProc")
    public ResponseEntity<CommonResponse<MsgDTO>> loginProc(@RequestBody UserInfoDTO pDTO,
                                                            HttpServletResponse response) {
        log.info("{}.loginProc Start!", getClass().getName());

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(pDTO.userId(), pDTO.password())
        );

        AuthInfo principal = (AuthInfo) auth.getPrincipal();
        UserInfoDTO u = principal.userInfoDTO();

        jwtTokenService.issueTokens(u, response);

        MsgDTO dto = MsgDTO.builder()
                .result(1)
                .msg("로그인 성공")
                .build();

        log.info("{}.loginProc End!", getClass().getName());

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), dto)
        );
    }

    @PostMapping("loginInfo")
    public ResponseEntity<CommonResponse<UserInfoDTO>> loginInfo(
            @AuthenticationPrincipal Jwt jwt) {

        log.info("{}.loginInfo Start!", getClass().getName());
        UserInfoDTO dto;

        if (jwt == null) {
            dto = UserInfoDTO.builder()
                    .userId("")
                    .userName("")
                    .roles("")
                    .build();
        } else {

            String userId = jwt.getSubject();
            String userName = jwt.getClaim("username");
            List<String> roles = jwt.getClaim("roles");
            String rolesCsv = (roles == null) ? "" : String.join(",", roles);

            dto = UserInfoDTO.builder()
                    .userId(userId)
                    .userName(userName)
                    .roles(rolesCsv)
                    .build();
        }

        log.info("{}.loginInfo End!", getClass().getName());

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), dto)
        );
    }
}
