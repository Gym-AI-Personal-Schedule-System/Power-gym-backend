package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.PrivilegeDTO;
import org.springframework.http.ResponseEntity;

public interface PrivilegeService {

    ResponseEntity<?> addPrivilege(PrivilegeDTO privilegeDTO) throws Exception;

    ResponseEntity<?> getAllPrivileges() throws Exception;

    ResponseEntity<?> assignPrivileges(PrivilegeDTO privilegeDTO) throws Exception;

    ResponseEntity<?> getRoleWisePrivilege(PrivilegeDTO privilegeDTO)throws Exception;

}
