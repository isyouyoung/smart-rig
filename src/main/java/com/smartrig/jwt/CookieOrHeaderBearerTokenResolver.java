package com.smartrig.jwt;

/**
 * Bearer 토큰 추출기 구현체.
 * <p>
 * 목적
 * 1) HttpOnly 쿠키(Access Token)을 최우선으로 읽고
 * 2) 쿠키에 없을 때만 표준 Authorization 헤더(Bearer ...)을 사용한다.
 * </p>
 * 보안 정책
 * - URL 쿼리스트링이나 form 파라미터로 전달되는 토큰은 허용하지 않는다.
 * - 토큰 값은 로깅 금지, 유출 위험이 있으므로 여기에서는 어떠한 로깅도 하지 않는다.
 * <p>
 * 스레드 - 세이프티
 * - 상태를 가지지 않는 불변 객체로 설계되었고, Spring Bean으로 실글턴 등록해도 안전하다.
 */
public class CookieOrHeaderBearerTokenResolver {
}
