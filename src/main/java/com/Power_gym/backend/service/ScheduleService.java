package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.ScheduleDTO;
import com.Power_gym.backend.DTO.ScheduleRequestDTO;
import com.Power_gym.backend.DTO.UserDTO;
import com.Power_gym.backend.DTO.common.RequestDTO;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    ResponseEntity<?> generateSchedule(ScheduleRequestDTO scheduleDTO) throws Exception;

    ResponseEntity<?> saveSchedule(ScheduleDTO scheduleDTO) throws Exception;

    ResponseEntity<?> getUserWiseSchedule(RequestDTO userDTO) throws Exception;
    ResponseEntity<?> getUserScheduleCreateDates(UserDTO userDTO) throws Exception;
}
