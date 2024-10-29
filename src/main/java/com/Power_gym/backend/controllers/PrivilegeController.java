package com.Power_gym.backend.controllers;

import com.Power_gym.backend.DTO.PrivilegeDTO;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/power-gym/privilege")
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;

    @PostMapping(value = "addPrivilege")
    ResponseEntity<?> addNewPrivilege(@RequestBody PrivilegeDTO privilegeDTO) throws Exception{
        try {
            return privilegeService.addPrivilege(privilegeDTO);
        } catch (CustomException e) {
            throw new CustomException("PR_0001 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("PR_0001 : "+e.getMessage());
        }
    }

    @PostMapping(value = "getAllPrivileges")
    ResponseEntity<?> getAllPrivileges() throws Exception{
        try {
            return privilegeService.getAllPrivileges();
        } catch (CustomException e) {
            throw new CustomException("PR_0002 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("PR_0002 : "+ e.getMessage());
        }
    }

    @PostMapping(value = "assignPrivileges")
    ResponseEntity<?> assignPrivileges(@RequestBody PrivilegeDTO privilegeDTO) throws Exception{
        try {
            return privilegeService.assignPrivileges(privilegeDTO);
        } catch (CustomException e) {
            throw new CustomException("PR_0003 : "+e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("PR_0003 : "+e.getMessage());
        }
    }

    @PostMapping(value = "/getRoleWisePrivileges")
    ResponseEntity<?> getRoleWisePrivileges(@RequestBody PrivilegeDTO privilegeDTO) throws Exception{
        try {
            return privilegeService.getRoleWisePrivilege(privilegeDTO);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
