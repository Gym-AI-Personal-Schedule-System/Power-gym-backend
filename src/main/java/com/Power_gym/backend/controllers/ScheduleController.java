package com.Power_gym.backend.controllers;

import com.Power_gym.backend.DTO.PrivilegeDTO;
import com.Power_gym.backend.DTO.ScheduleDTO;
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
    ResponseEntity<?> addNewPrivilege(@RequestBody ScheduleDTO scheduleDTO) throws Exception{
        try {
            return scheduleService.getSchedule(scheduleDTO);
        } catch (CustomException e) {
            throw new CustomException("SH_0001 : "+ e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("SH_0001 : "+e.getMessage());
        }
    }
}
