package com.smartrig.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Spring Security의 핵심 설정
 * <p>
 * 목표
 * - 세션 완전 미사용
 * - 스프링 리소스 서버(OAuth2 Resource Sever) + JWT 인증
 * - Access Token은 쿠키에서 먼저 읽고, 없으면 Authorization 헤더 사용
 * 정적 리소스/공개 API는 인증 없이 접근 허용
 * 로그아웃 시 쿠키 정리
 * </p>
 * 구성 요약
 * - PasswordEncoder: 회원가입/비밀번호 변경시 해시용
 * - AuthenticationManager: /login API에서 사용자 인증
 * - SecurityFilterChain: 인가 규칙, 리소스 서버(JWT), 세션/CSRF/CORS 등 보안 정책
 * <p>
 * 참고
 * - 리소스 서버의 jwt 검증기/권한변환기는 JwtConfig 에서 빈으로 제공(JwtDecoder, JwtAuthenticationConverter).
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
}
