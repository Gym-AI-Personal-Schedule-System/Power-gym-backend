package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.ScheduleDTO;
import com.Power_gym.backend.DTO.ScheduleRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    ResponseEntity<?> getSchedule(ScheduleRequestDTO scheduleDTO) throws Exception;

    ResponseEntity<?> saveSchedule(ScheduleDTO scheduleDTO) throws Exception;
}
