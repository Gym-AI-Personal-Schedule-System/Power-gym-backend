package com.Power_gym.backend.controllers;

import com.Power_gym.backend.DTO.ScheduleDTO;
import com.Power_gym.backend.DTO.ScheduleRequestDTO;
import com.Power_gym.backend.DTO.UserDTO;
import com.Power_gym.backend.DTO.common.RequestDTO;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/power-gym/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @PostMapping(value = "generateSchedule")
    ResponseEntity<?> generateSchedule(@RequestBody ScheduleRequestDTO scheduleDTO) throws Exception{
        try {
            return scheduleService.generateSchedule(scheduleDTO);
        } catch (CustomException e) {
            throw new CustomException("SH_0001 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("SH_0001 : "+e.getMessage());
        }
    }

    @PostMapping(value = "saveSchedule")
    ResponseEntity<?> saveSchedule(@RequestBody ScheduleDTO scheduleDTO) throws Exception{
        try {
            return scheduleService.saveSchedule(scheduleDTO);
        } catch (CustomException e) {
            throw new CustomException("SH_0002 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("SH_0002 : "+e.getMessage());
        }
    }

    @PostMapping(value = "getUserWiseSchedule")
    ResponseEntity<?> getUserWiseSchedule(@RequestBody RequestDTO requestDTO) throws Exception{
        try {
            return scheduleService.getUserWiseSchedule(requestDTO);
        } catch (CustomException e) {
            throw new CustomException("SH_0003 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("SH_0003 : "+e.getMessage());
        }


    }

    @PostMapping(value = "getUserScheduleCreateDates")
    ResponseEntity<?> getUserScheduleCreateDates(@RequestBody UserDTO userDTO) throws Exception{
        try {
            return scheduleService.getUserScheduleCreateDates(userDTO);
        } catch (CustomException e) {
            throw new CustomException("SH_0004 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("SH_0004 : "+e.getMessage());
        }

    }

    @GetMapping(value = "getSchedule")
    ResponseEntity<?> getSchedule() throws Exception{
        try {
            return scheduleService.getSchedule();
        } catch (CustomException e) {
            throw new CustomException("SH_0005 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("SH_0005 : "+e.getMessage());
        }

    }




}
