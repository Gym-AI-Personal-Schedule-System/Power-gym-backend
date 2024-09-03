package com.Power_gym.backend.DTO;

import com.Power_gym.backend.models.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserScheduleDetailDTO {
    private Integer scheduleDetailID;
    private Integer age;
    private String gender;
    private Integer height;
    private Integer experience;
    private Integer weight;
    private Double bmi;
    private Integer workoutTime;
    private String fitnessGoal;
    private String createTime;
    private Integer isActive;
    private Schedule schedule;
    private Integer userId;
}
