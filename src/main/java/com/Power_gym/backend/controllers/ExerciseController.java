package com.Power_gym.backend.controllers;

import com.Power_gym.backend.DTO.ExerciseDTO;
import com.Power_gym.backend.DTO.ExerciseDetailsDTO;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.models.ExerciseDetails;
import com.Power_gym.backend.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("/api/v1/power-gym/exercise")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @PostMapping(value = "saveExercise")
    ResponseEntity<?>saveExercise(@RequestBody ExerciseDTO exerciseDTO)throws Exception{
        try {
           return exerciseService.saveExercise(exerciseDTO);
        }catch (CustomException e){
            throw new CustomException("EX001  : "+e.getMessage());
        }catch (Exception e){
            throw new CustomException("EX001  : "+e.getMessage());
        }
    }

    @PostMapping(value = "scheduleWiseExerciseSave")
    ResponseEntity<?>scheduleWiseExerciseSave(@RequestBody ExerciseDetailsDTO detailsDTO)throws Exception{
        try {
            return exerciseService.scheduleWiseExerciseSave(detailsDTO);
        }catch (CustomException e){
            throw new CustomException("EX002  : " +e.getMessage());
        }catch (Exception e){
            throw new CustomException("EX002  : " +e.getMessage());
        }
    }
}
