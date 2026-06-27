package com.smartrig.service;

import com.smartrig.dto.UserInfoDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserInfoService extends UserDetailsService {

    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    int insertUserInfo(UserInfoDTO pDTO);

    UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception;

}