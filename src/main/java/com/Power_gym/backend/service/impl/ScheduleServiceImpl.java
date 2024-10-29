package com.Power_gym.backend.service.impl;

import com.Power_gym.backend.DTO.*;
import com.Power_gym.backend.DTO.common.RequestDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.models.*;
import com.Power_gym.backend.repository.*;
import com.Power_gym.backend.service.ScheduleService;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserScheduleDetailsRepository userScheduleDetailsRepository;
    @Autowired
    private ExerciseDetailsRepository exerciseDetailsRepository;
    @Autowired
    private UserScheduleRepository userScheduleRepository;

    Gson gson = new Gson();

    @Value("${powerGym.app.pythonUrl}")
    private String url;

    public ScheduleServiceImpl(ModelMapper modelMapper, RestTemplate restTemplate) {
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }


    @Override
    @Transactional
    public ResponseEntity<?> generateSchedule(ScheduleRequestDTO scheduleDTO) throws Exception {

        Optional<User> userData = Optional.ofNullable(userRepository.findByMobileNum(scheduleDTO.getMobileNumOne())
                .orElseThrow(() -> new CustomException("User not found with mobile number")));

//        ApiResponseDTO schedule = getSchedule(scheduleDTO);
        ApiResponseDTO schedule = Optional.ofNullable(getSchedule(scheduleDTO))
                .orElseThrow(() -> new CustomException("Cannot found a Schedule "));


        // Extract the prediction and determine the schedule
        Schedule scheduleData = determineScheduleFromPrediction(schedule.getPrediction());

        // Create JSON response object
        ApiResponseDTO responseDTO = ApiResponseDTO.builder().scheduleValue(scheduleData.getScheduleName()).build();


        List<UserSchedule> userSchedules = saveUserScheduleDetails(userData.get(), scheduleData, scheduleDTO);

        List<UserScheduleDTO> userScheduleDTOList = userSchedules.stream().map(userSchedule -> {
                            return UserScheduleDTO.builder()
                                    .userScheduleID(userSchedule.getUserScheduleID())
                                    .exerciseDetails(convertEntityToExerciseDetailDTO(userSchedule.getExerciseDetails()))
                                    .userScheduleDetails(convertUserDetailsScheduleToUserScheduleDetailDto(userSchedule.getUserScheduleDetails()))
                                    .isActive(userSchedule.getIsActive())
                                    .build();
                        }

                )
                .toList();
        responseDTO.setScheduleDTOList(userScheduleDTOList);

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", responseDTO), HttpStatus.OK);

    }

    private ExerciseDetailsDTO convertEntityToExerciseDetailDTO(ExerciseDetails exerciseDetails) {
        return ExerciseDetailsDTO.builder()
                .exerciseDetailId(exerciseDetails.getExerciseDetailId())
                .exercise(modelMapper.map(exerciseDetails.getExercise(), ExerciseDTO.class))
                .schedule(modelMapper.map(exerciseDetails.getSchedule(), ScheduleDTO.class))
                .sets(exerciseDetails.getSets())
                .experience(exerciseDetails.getExperience())
                .isActive(exerciseDetails.getIsActive()).build();
    }

    private UserScheduleDetailDTO convertUserDetailsScheduleToUserScheduleDetailDto(UserScheduleDetails userScheduleDetail) {
        return UserScheduleDetailDTO.builder()
                .scheduleDetailID(userScheduleDetail.getUserScheduleDetailID())
                .age(userScheduleDetail.getAge())
                .gender(userScheduleDetail.getGender())
                .height(userScheduleDetail.getHeight())
                .experience(userScheduleDetail.getExperience())
                .weight(userScheduleDetail.getWeight())
                .bmi(userScheduleDetail.getBmi())
                .workoutTime(userScheduleDetail.getWorkoutTime())
                .fitnessGoal(userScheduleDetail.getFitnessGoal())
                .createTime(userScheduleDetail.getCreateTime() + "")
                .isActive(userScheduleDetail.getIsActive())
                .userId(userScheduleDetail.getUser().getId()).build();

    }

    private ApiResponseDTO getSchedule(ScheduleRequestDTO scheduleDTO) throws CustomException {

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
            System.out.println("AI response :" + responseEntity.getBody());
            // Parse the response using Gson
            ApiResponseDTO apiResponseDTO = gson.fromJson(responseEntity.getBody(), ApiResponseDTO.class);

            return apiResponseDTO;
            // Return the response from the Python endpoint
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


    private Schedule determineScheduleFromPrediction(List<List<Integer>> prediction) {
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
                return getPredictionWorkoutNoWiseSchedule(-1);
//                return "Gain Game Plan";
            case 0:
                return getPredictionWorkoutNoWiseSchedule(0);
//                return "Bulk Up Blueprint";
            case 1:
                return getPredictionWorkoutNoWiseSchedule(1);
//                return "Fat Burn Fiesta";
            case 2:
                return getPredictionWorkoutNoWiseSchedule(2);
//                return "Fit Life Routine";
            case 3:
                return getPredictionWorkoutNoWiseSchedule(3);
//                return "Gain Game Plan";
            case 4:
                return getPredictionWorkoutNoWiseSchedule(4);
//                return "Lady Bulk Blueprint";
            case 5:
                return getPredictionWorkoutNoWiseSchedule(5);
//                return "Lean Machine Schedule";
            case 6:
                return getPredictionWorkoutNoWiseSchedule(6);
//                return "Power Packed Woman Plan";
            default:
                return null;
//                return "Unknown Schedule";
        }


    }

    private Schedule getPredictionWorkoutNoWiseSchedule(int predictionNo) {
        return scheduleRepository.getSchedulesByWorkoutNo(predictionNo).get();
    }


    private List<UserSchedule> saveUserScheduleDetails(User userData, Schedule schedule, ScheduleRequestDTO scheduleDTO) throws CustomException {
        try {
            List<ExerciseDetails> exerciseList = exerciseDetailsRepository.findAllByIsActiveAndScheduleAndExperience(1, schedule.getScheduleID(), scheduleDTO.getWorkoutExperience());
            if (exerciseList == null || exerciseList.isEmpty()) {
                throw new CustomException("No exercises found for the given schedule and experience level.");
            }

            UserScheduleDetails userScheduleDetails = UserScheduleDetails.builder()
                    .user(userData)
                    .age(scheduleDTO.getAge())
                    .bmi(scheduleDTO.getBmi())
                    .createTime(new Date())
                    .experience(scheduleDTO.getWorkoutExperience())
                    .fitnessGoal(scheduleDTO.getFitnessGoal())
                    .gender(scheduleDTO.getGender())
                    .height(scheduleDTO.getHeight())
                    .weight(scheduleDTO.getWeight())
                    .isActive(1)
                    .workoutTime(scheduleDTO.getWorkoutTime()).build();
            UserScheduleDetails savedUserScheduleDetails = userScheduleDetailsRepository.save(userScheduleDetails);

            List<UserSchedule> userScheduleList = exerciseList.stream().map(exercise -> {
                var userSchedule = new UserSchedule();
                userSchedule.setUserScheduleDetails(savedUserScheduleDetails);
                userSchedule.setExerciseDetails(exercise);
                userSchedule.setIsActive(1);
                return userSchedule;
            }).toList(); // Convert to List

            userScheduleRepository.saveAll(userScheduleList);

            return userScheduleList;

        } catch (DataAccessException dae) {
            throw new CustomException("Database error while saving user schedule details: " + dae.getMessage());
        }

    }


    @Override
    public ResponseEntity<?> saveSchedule(ScheduleDTO scheduleDTO) throws Exception {
        if (scheduleDTO.getScheduleName() == null || scheduleDTO.getScheduleName().isEmpty()) {
            throw new CustomException("Schedule Name is empty");
        }
        // Convert ScheduleDTO to Schedule entity using ModelMapper
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        schedule.setCreateTime(new Date());
        schedule.setIsActive(1);
        schedule = scheduleRepository.save(schedule);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", schedule), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserWiseSchedule(RequestDTO userDTO) throws CustomException {
        if(userDTO.getUserCode().isEmpty()||userDTO.getCreatDate().isEmpty()){
            throw new CustomException("User Code or Create Date is empty");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date createDate;
        try {
            // Parse the String date (userDTO.getCreatDate()) into a java.util.Date
            createDate = dateFormat.parse(userDTO.getCreatDate());
        } catch (ParseException e) {
            // Handle ParseException if the input date string is in an invalid format
            throw new CustomException("Invalid date format. Expected format is yyyy-MM-dd");
        }

        List<UserScheduleDetails> allUserScheduleDetailsByUserCode = userScheduleDetailsRepository.getAllUserScheduleDetailByUserCodeAndDate(userDTO.getUserCode(),createDate, 1);
        if (!allUserScheduleDetailsByUserCode.isEmpty()) {
            List<UserSchedule> userSchedules = userScheduleRepository.getAllUserScheduleByScheduleDetails(allUserScheduleDetailsByUserCode);
            if (userSchedules.isEmpty()) {
                throw new CustomException("User Schedule exercises is empty!");
            }
            List<UserScheduleDTO> userScheduleDTOList = userSchedules.stream().map(userSchedule -> {
                                return UserScheduleDTO.builder()
                                        .userScheduleID(userSchedule.getUserScheduleID())
                                        .exerciseDetails(convertEntityToExerciseDetailDTO(userSchedule.getExerciseDetails()))
                                        .userScheduleDetails(convertUserDetailsScheduleToUserScheduleDetailDto(userSchedule.getUserScheduleDetails()))
                                        .isActive(userSchedule.getIsActive())
                                        .build();
                            }

                    )
                    .toList();
            return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", userScheduleDTOList), HttpStatus.OK);

        }
        throw new CustomException("User Schedule details is empty!");
    }

    @Override
    public ResponseEntity<?> getUserScheduleCreateDates(UserDTO userDTO) throws Exception {
        List<UserScheduleDetails> detailsList = userScheduleDetailsRepository.getAllUserScheduleDetailsByUserCode(userDTO.getUserCode(), 1);
        if (detailsList.isEmpty()) {
            throw new CustomException("User Schedule Data is empty");
        }
        List<UserScheduleDetailDTO> scheduleDetailDTOS = detailsList.stream().map(detail ->
                UserScheduleDetailDTO.builder()
                .createTime(detail.getCreateTime() + "").build()).toList();
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", scheduleDetailDTOS), HttpStatus.OK);
    }
}
