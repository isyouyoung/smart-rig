package com.smartrig.service.impl;

import com.smartrig.dto.UserInfoDTO;
import com.smartrig.service.IJwtTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenService implements IJwtTokenService {

    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    private final JwtEncoder jwtEncoder;

    @Value("${jwt.token.creator}")
    private String issuer;

    @Value("${jwt.token.access.valid.time}")
    private long accessTtlSec;

    @Value("${jwt.token.refresh.valid.time}")
    private long refreshTtlSec;

    @Value("${jwt.token.access.name}")
    private String accessCookie;

    @Value("${jwt.token.refresh.name}")
    private String refreshCookie;

    private String encode(UserInfoDTO user, long ttlSec, String type) {
        Instant now = Instant.now();
        List<String> roles = splitRoles(user.roles());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ttlSec))
                .subject(user.userId())
                .claim(CLAIM_USERNAME, user.userName())
                .claim(CLAIM_TYPE, type)
                .claim(CLAIM_ROLES, roles)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    private static List<String> splitRoles(String roles) {
        if (roles == null || roles.isBlank()) return List.of("USER");
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public String generateAccessToken(UserInfoDTO user) {
        return encode(user, accessTtlSec, TYPE_ACCESS);
    }

    @Override
    public String generateRefreshToken(UserInfoDTO user) {
        return encode(user, refreshTtlSec, TYPE_REFRESH);
    }

    @Override
    public void writeTokensAsCookies(HttpServletResponse res, String accessToken, String refreshToken) {
        ResponseCookie at = ResponseCookie.from(accessCookie, accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(accessTtlSec)
                .build();

        ResponseCookie rt = ResponseCookie.from(refreshCookie, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(refreshTtlSec)
                .build();

        // Set Cookie 헤더는 여러번 추가 가능하므로 각각 추가
        res.addHeader("Set-Cookie", at.toString());
        res.addHeader("Set-Cookie", rt.toString());
    }
}
