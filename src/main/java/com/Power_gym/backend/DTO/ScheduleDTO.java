package com.Power_gym.backend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleDTO {
    private int age;
    private int workoutExperience;
    private int workoutTime;
    private double weight;
    private double height;
    private double bmi;
    private String gender;
    private String fitnessGoal;
}
