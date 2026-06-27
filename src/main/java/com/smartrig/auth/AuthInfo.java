package com.smartrig.auth;

import com.smartrig.dto.userInfoDTO;
import com.smartrig.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @param userInfoDTO 로그인된 사용자 정보 UserInfoRepository로부터 조회된 정보를 저장하기 위한 객체
 */

@Slf4j
public record AuthInfo(UserInfoDTO userInfoDTO) implements UserDetails {

}
