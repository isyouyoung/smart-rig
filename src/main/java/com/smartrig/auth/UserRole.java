package com.smartrig.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 사용자 권한을 정의하는 Enum
// Enum은 정해진 값만 사용할 수 있어서 오타나 실수를 방지할 수 있음
@AllArgsConstructor // Lombok이 생성자를 자동으로 만들어줌 → ADMIN("ROLE_ADMIN") 이렇게 값을 넣을 수 있음
@Getter // Lombok이 getValue() 메서드를 자동으로 만들어줌 → userRole.getValue() 로 "ROLE_ADMIN" 가져올 수 있음
public enum UserRole {

    // ADMIN → 관리자 권한
    // Spring Security는 권한 앞에 "ROLE_" 을 붙이는 게 규칙
    // 그래서 우리가 ADMIN 이라고 부르지만 Security한테 넘길 땐 "ROLE_ADMIN" 으로 넘겨야 함
    ADMIN("ROLE_ADMIN"),

    // USER → 일반 사용자 권한
    USER("ROLE_USER");

    // ROLE_ADMIN, ROLE_USER 문자열을 저장하는 변수
    // private 이라 외부에서 직접 접근 못하고 getValue() 로만 가져올 수 있음
    private final String value;
}