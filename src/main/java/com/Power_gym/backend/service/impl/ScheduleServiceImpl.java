package com.Power_gym.backend.service.impl;

import com.Power_gym.backend.DTO.ScheduleDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final RestTemplate restTemplate;

    public ScheduleServiceImpl(ModelMapper modelMapper, RestTemplate restTemplate) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }


    @Value("${powerGym.app.pythonUrl}")
    private  String url;

    @Override
    public ResponseEntity<?> getSchedule(ScheduleDTO scheduleDTO)throws Exception {

        if (scheduleDTO.getAge() < 0 ) {
            throw new CustomException("Age is empty or 0");
        }
        if (scheduleDTO.getWorkoutExperience() < 0) {
            throw new CustomException("Workout Experience is empty");
        }
        if (scheduleDTO.getWorkoutTime() < 0) {
            throw new CustomException("Workout Time is empty");
        }
        if (scheduleDTO.getWeight() < 0) {
            throw new CustomException("Weight is empty");
        }
        if (scheduleDTO.getHeight() < 0) {
            throw new CustomException("Height is empty");
        }
        if (scheduleDTO.getBmi() < 0) {
            throw new CustomException("BMI is empty");
        }
        if (scheduleDTO.getGender() == null || scheduleDTO.getGender().trim().isEmpty()) {
            throw new CustomException("Gender is empty");
        }
        if (scheduleDTO.getFitnessGoal() == null || scheduleDTO.getFitnessGoal().trim().isEmpty()) {
            throw new CustomException("Fitness Goal is empty");
        }
        // Define the Python endpoint URL
        String pythonUrl = url;

        // Prepare the request payload using data from ScheduleDTO
        Map<String, Object> payload = new HashMap<>();
        payload.put("age", scheduleDTO.getAge());
        payload.put("workout_experience", scheduleDTO.getWorkoutExperience());
        payload.put("workout_time", scheduleDTO.getWorkoutTime());
        payload.put("weight", scheduleDTO.getWeight());
        payload.put("height", scheduleDTO.getHeight());
        payload.put("bmi", scheduleDTO.getBmi());
        payload.put("gender", scheduleDTO.getGender());
        payload.put("fitness_goal", scheduleDTO.getFitnessGoal());



        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the headers and payload
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            // Make the POST request to the Python endpoint
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    pythonUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Return the response from the Python endpoint
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success",responseEntity.getBody()), HttpStatus.OK);
        } catch (Exception e) {
            // Handle any exceptions that occur during the HTTP request
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_GATEWAY.value(), "Error" ,"Error while calling Python endpoint: " + e.getMessage()), HttpStatus.BAD_GATEWAY);
        }
    }
}
