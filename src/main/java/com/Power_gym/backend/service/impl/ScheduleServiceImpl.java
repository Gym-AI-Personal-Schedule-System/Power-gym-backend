package com.Power_gym.backend.service.impl;

import com.Power_gym.backend.DTO.ApiResponseDTO;
import com.Power_gym.backend.DTO.ScheduleDTO;
import com.Power_gym.backend.DTO.ScheduleRequestDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.models.Schedule;
import com.Power_gym.backend.repository.ScheduleRepository;
import com.Power_gym.backend.service.ScheduleService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    ScheduleRepository scheduleRepository;

    Gson gson = new Gson();

    @Value("${powerGym.app.pythonUrl}")
    private  String url;

    public ScheduleServiceImpl(ModelMapper modelMapper, RestTemplate restTemplate) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }



    @Override
    public ResponseEntity<?> getSchedule(ScheduleRequestDTO scheduleDTO)throws Exception {

        // Validate inputs
        validateInputs(scheduleDTO);
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
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    pythonUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            // Check for a successful response
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new CustomException("Failed to retrieve schedule: " + responseEntity.getStatusCode());
            }
            System.out.println("response :" +responseEntity.getBody());
            // Parse the response using Gson
            ApiResponseDTO apiResponseDTO = gson.fromJson(responseEntity.getBody(), ApiResponseDTO.class);

            // Extract the prediction and determine the schedule
            String schedule = determineScheduleFromPrediction(apiResponseDTO.getPrediction());

            // Create JSON response object
            ApiResponseDTO responseDTO =  ApiResponseDTO.builder().scheduleValue(schedule).build();


            // Return the response from the Python endpoint
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success",responseDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private void validateInputs(ScheduleRequestDTO scheduleDTO) throws CustomException {
        if (scheduleDTO.getAge() < 0) {
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
    }



    private String determineScheduleFromPrediction(List<List<Integer>> prediction) {
        int rowPosition = -1;
        int colPosition = -1;

        // Find the position of '1' in the prediction matrix
        for (int i = 0; i < prediction.size(); i++) {
            List<Integer> row = prediction.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) == 1) {
                    rowPosition = i;
                    colPosition = j;
                    break;
                }
            }
            if (rowPosition != -1 && colPosition != -1) {
                break;
            }
        }

        switch (colPosition) {
            case -1:
                return "Gain Game Plan";
            case 0:
                return "Bulk Up Blueprint";
            case 1:
                return "Fat Burn Fiesta";
            case 2:
                return "Fit Life Routine";
            case 3:
                return "Gain Game Plan";
            case 4:
                return "Lady Bulk Blueprint";
            case 5:
                return "Lean Machine Schedule";
            case 6:
                return "Power Packed Woman Plan";
            default:
                return "Unknown Schedule";
        }

    }

    private boolean saveScheduleDetails(int scheduleNO){
    return true;
    }

    @Override
    public ResponseEntity<?> saveSchedule(ScheduleDTO scheduleDTO)throws Exception {
        if (scheduleDTO.getScheduleName() == null || scheduleDTO.getScheduleName().isEmpty()) {
            throw new CustomException("Schedule Name is empty");
        }
        // Convert ScheduleDTO to Schedule entity using ModelMapper
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        schedule.setCreateTime(new Date());
        schedule.setIsActive(1);
        schedule = scheduleRepository.save(schedule);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success",schedule), HttpStatus.OK);
    }
}
