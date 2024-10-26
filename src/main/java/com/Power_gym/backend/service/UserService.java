package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.UserDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getUserDataByMobileNumber(UserDTO userDTO) throws Exception;
    ResponseMessage otpSend(UserDTO userData) throws Exception;

    ResponseMessage updatePassword(UserDTO userData)throws Exception;
}
