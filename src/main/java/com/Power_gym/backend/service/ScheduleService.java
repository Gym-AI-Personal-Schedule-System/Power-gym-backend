package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.*;
import com.Power_gym.backend.DTO.common.RequestDTO;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    ResponseEntity<?> generateSchedule(ScheduleRequestDTO scheduleDTO) throws Exception;
    ResponseEntity<?> saveSchedule(ScheduleDTO scheduleDTO) throws Exception;
    ResponseEntity<?> getUserWiseSchedule(RequestDTO userDTO) throws Exception;
    ResponseEntity<?> getAllUserScheduleDetails(UserDTO userDTO) throws Exception;
    ResponseEntity<?> getSchedule()throws Exception;
    ResponseEntity<?> saveScheduleWiseExerciseList(ExerciseDataDetailDTO detailsDTO)throws Exception;
    ResponseEntity<?> getScheduleCount()throws Exception;
}
