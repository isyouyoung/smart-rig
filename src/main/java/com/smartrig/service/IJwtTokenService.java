package com.smartrig.service;

import jakarta.servlet.http.HttpServletResponse;
import com.smartrig.dto.UserInfoDTO;

public interface IJwtTokenService {

    String generateAccessToken(UserInfoDTO user);

    String generateRefreshToken(UserInfoDTO user);

    void writeTokensAsCookies(HttpServletResponse res, String accessToken, String refreshToken);

    default void issueTokens(UserInfoDTO user, HttpServletResponse res) {
        String at = generateAccessToken(user);
        String rt = generateRefreshToken(user);
        writeTokensAsCookies(res, at, rt);
    }
}
