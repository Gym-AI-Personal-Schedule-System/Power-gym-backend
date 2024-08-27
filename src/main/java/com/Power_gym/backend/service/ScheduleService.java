package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.ScheduleDTO;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    ResponseEntity<?> getSchedule(ScheduleDTO scheduleDTO) throws Exception;
}
