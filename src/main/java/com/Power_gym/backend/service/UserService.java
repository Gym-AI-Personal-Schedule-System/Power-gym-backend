package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.RoleDTO;
import com.Power_gym.backend.DTO.UserDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getUserDataByMobileNumber(UserDTO userDTO) throws Exception;
    ResponseMessage otpSend(UserDTO userData) throws Exception;

    ResponseMessage updatePassword(UserDTO userData)throws Exception;

    ResponseMessage getActiveMemberList()throws Exception;

    ResponseMessage getAllActiveUserList()throws Exception;

    ResponseMessage getAgeWiseMemberCount()throws Exception;

    ResponseMessage getActiveUserCount(RoleDTO roleDTO)throws Exception;
}
