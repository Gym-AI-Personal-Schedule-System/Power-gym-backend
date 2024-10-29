package com.Power_gym.backend.service.impl;

import com.Power_gym.backend.DTO.ExerciseDTO;
import com.Power_gym.backend.DTO.ExerciseDetailsDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.models.Exercise;
import com.Power_gym.backend.models.ExerciseDetails;
import com.Power_gym.backend.models.Schedule;
import com.Power_gym.backend.repository.ExerciseDetailsRepository;
import com.Power_gym.backend.repository.ExerciseRepository;
import com.Power_gym.backend.repository.ScheduleRepository;
import com.Power_gym.backend.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.NotContextException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    ExerciseDetailsRepository exerciseDetailsRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveExercise(ExerciseDTO exerciseDTO) throws Exception {
        if (!exerciseDTO.getExerciseName().isEmpty() && !exerciseDTO.getVideo_url().isEmpty()) {
            Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
            exercise.setIsActive(1);
            Exercise save = exerciseRepository.save(exercise);
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", save), HttpStatus.OK);

        }
        throw new CustomException("empty values");

    }

    @Override
    public ResponseEntity<?> scheduleWiseExerciseSave(ExerciseDetailsDTO detailsDTO) throws Exception {
        if (detailsDTO.getExerciseId() > 0 && detailsDTO.getScheduleID() > 0) {
            Optional<Schedule> schedule = scheduleRepository.getSchedulesByScheduleID(detailsDTO.getScheduleID());
            Optional<Exercise> exercise = exerciseRepository.getExerciseByExerciseId(detailsDTO.getExerciseId());

            if (schedule.isEmpty()) {
                throw new CustomException("Cannot find a schedule !");
            }
            if (exercise.isEmpty()) {
                throw new CustomException("Cannot find a exercise !");
            }

            ExerciseDetails details = modelMapper.map(detailsDTO, ExerciseDetails.class);
            details.builder().schedule(schedule.get()).exercise(exercise.get()).isActive(1).build();
            ExerciseDetails save = exerciseDetailsRepository.save(details);
            if (save.getExercise() != null) {
                return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", save), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Something went wrong !"), HttpStatus.BAD_REQUEST);


        }
        throw new CustomException("Empty Values !");
    }

    @Override
    public ResponseEntity<?> getActiveExerciseList() throws Exception {
        List<Exercise> dataList = exerciseRepository.findAllByIsActive(1);
        if (dataList.isEmpty()) throw new CustomException("Exercise Data is empty !");

        List<ExerciseDTO> exerciseDTOS = dataList.stream()
                .map(exercise -> new ExerciseDTO(
                        exercise.getExerciseId(),
                        exercise.getExerciseName(),
                        exercise.getVideo_url(),
                        exercise.getIsActive()
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", exerciseDTOS), HttpStatus.OK);

    }


}
