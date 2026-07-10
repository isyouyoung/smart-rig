package com.smartrig.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Model 관련 요청을 처리하는 Controller이다.
// 클라이언트의 요청을 받아 Service 계층으로 전달한다.
@RestController
@RequestMapping(value = "/model/v1")
public class ModelController {

}