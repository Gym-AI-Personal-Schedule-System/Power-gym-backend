package com.Power_gym.backend.service;

import com.Power_gym.backend.DTO.ExerciseDTO;
import com.Power_gym.backend.DTO.ExerciseDetailsDTO;
import org.springframework.http.ResponseEntity;

public interface ExerciseService {
    ResponseEntity<?> saveExercise(ExerciseDTO exerciseDTO)throws Exception;
    ResponseEntity<?> scheduleWiseExerciseSave(ExerciseDetailsDTO detailsDTO)throws Exception;

    ResponseEntity<?> getActiveExerciseList()throws Exception;
}
