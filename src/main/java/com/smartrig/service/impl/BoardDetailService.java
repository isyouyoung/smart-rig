package com.smartrig.service.impl;

import com.smartrig.service.IBoardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 이 클래스가 비즈니스 로직을 처리하는 서비스 클래스임을 JPA/스프링에 등록한다는 뜻
@Service
// final이 붙은 필드를 이용해 생성자 주입(DI) 코드를 자동으로 생성한다.
@RequiredArgsConstructor
public class BoardDetailService implements IBoardDetailService {

    // 추후 BoardDetailRepository 주입 및 메인보드 조회/저장 비즈니스 로직 구현 예정

}