package com.Power_gym.backend.controllers;

import com.Power_gym.backend.DTO.PrivilegeDTO;
import com.Power_gym.backend.DTO.UserDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("/api/v1/power-gym/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "getUserDataByMobileNumber")
    ResponseEntity<?> getUserDataByMobileNumber(@RequestBody UserDTO userDTO) throws Exception{
            return userService.getUserDataByMobileNumber(userDTO);
    }
    @PostMapping("/otpSend")
    public ResponseEntity<ResponseMessage> otpSend(@RequestBody UserDTO userData) throws Exception {
        return ResponseEntity.ok(userService.otpSend(userData));
    }
    @PutMapping("/updatePassword")
    public ResponseEntity<ResponseMessage> updateUser(@RequestBody UserDTO userData) throws Exception {
        return ResponseEntity.ok(userService.updatePassword(userData));
    }

    @GetMapping("/getActiveMemberList")
    public ResponseEntity<ResponseMessage> updateUser() throws Exception {
        return ResponseEntity.ok(userService.getActiveMemberList());
    }
    @GetMapping("/getAllActiveUserList")
    public ResponseEntity<ResponseMessage> getAllActiveUserList() throws Exception {
        return ResponseEntity.ok(userService.getAllActiveUserList());
    }
    @GetMapping("/getAgeWiseMemberCount")
    public ResponseEntity<ResponseMessage> getAgeWiseMemberCount() throws Exception {
        return ResponseEntity.ok(userService.getAgeWiseMemberCount());
    }
}
