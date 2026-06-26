package com.smartrig.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

/**
 * Bearer 토큰 추출기 구현체.
 * <p>
 * 목적
 * 1) HttpOnly 쿠키(Access Token)을 최우선으로 읽고
 * 2) 쿠키에 없을 때만 표준 Authorization 헤더(Bearer ...)을 사용한다.
 *  HttpOnly 쿠키로 저장할꺼니까 쿠키를 우선으로 먼저 읽는거임 없으면 헤더에서 꺼냄
 * </p>
 * 보안 정책
 * - URL 쿼리스트링이나 form 파라미터로 전달되는 토큰은 허용하지 않는다.
 *  *쿼리스트링은 인터넷 주소(URL) 뒤에 ?를 붙이고 Key=Value 형태로 데이터를 주렁주렁 매달아서 서버로 보내는 방식을 말합니다.*
 *  왜 쿼리스트링으로 온 토큰은 "거부"해야 할까? (보안 대참사 방지)
 *  "주소창에 내 소중한 비밀번호(토큰)가 그대로 노출되기 때문"
 *  마치 우리가 뵈웠던 get이랑 같음 🔍 GET 방식과 쿼리스트링은 한 몸입니다
 * - 토큰 값은 로깅 금지, 유출 위험이 있으므로 여기에서는 어떠한 로깅도 하지 않는다.
 * <p>
 * 스레드 - 세이프티
 * - 상태를 가지지 않는 불변 객체로 설계되었고, Spring Bean으로 실글턴 등록해도 안전하다.
 */
public class CookieOrHeaderBearerTokenResolver implements BearerTokenResolver {

    private final String cookieName;
    // Access Token이 저장된 쿠키 이름

    private final DefaultBearerTokenResolver delegate;

    public CookieOrHeaderBearerTokenResolver(String cookieName) {
        this.cookieName = cookieName;

        this.delegate = new DefaultBearerTokenResolver();
        // 보안상 허용하지 않음: URL 파라미터 또는 폼 파라미터로 토큰 전달
        this.delegate.setAllowFormEncodedBodyParameter(false);
        this.delegate.setAllowUriQueryParameter(false);
        // 필요시 헤더의 "Bear" 접두사 대소문자 허용 옵션 등을 조정할 수 있다(기본값유지).
    }


    @Override
    public String resolve(HttpServletRequest request) {
        // 1) 쿠키 우선
        String fromCookie = extractFromCookie(request);
        if (fromCookie != null && !fromCookie.isEmpty()) {
            return fromCookie;
        }

        // 2) 표준 헤더 파서에 위임 (Authorization: Bearer xxx)
        return delegate.resolve(request);
    }

    /**
     * 쿠키에서 토큰 값을 찾아 반환
     * - 값이 "Bearer xxx" 형태로 저장된 경우 접두사 제거
     *  - 앞뒤 공백, 따옴표 등을 방어적으로 제거
     */
    private String extractFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie c : cookies) {
            if (cookieName.equals(c.getName())) {
                String v = c.getValue();
                if (v == null) return null;

                // 공백 제거 및 간단한 정규화
                v = v.trim();
                if (v.isEmpty()) return null;

                // 일부 프록시/클라이언트가 값에 따옴표를 둘러싸는 경우 방지
                if ((v.startsWith("\"") && v.endsWith("\"")) || (v.startsWith("'") && v.endsWith("'"))) {
                    v = v.substring(1, v.length() -1).trim();
                }

                // 혹시 "Bearer ..." 형태로 저장했다면 접두사 제거 (대소문자 무시)
                final String BEARER_PREFIX = "bearer ";
                if (v.length() > BEARER_PREFIX.length()
                        && v.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
                    v = v.substring(BEARER_PREFIX.length()).trim();
                }

                return v.isEmpty() ? null : v;
            }
        }
        return null;
    }
}
