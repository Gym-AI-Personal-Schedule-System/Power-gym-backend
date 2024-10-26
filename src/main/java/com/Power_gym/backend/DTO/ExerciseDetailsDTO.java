package com.Power_gym.backend.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDetailsDTO {
    private Integer exerciseDetailId;
    private ExerciseDTO exercise;
    private ScheduleDTO schedule;
    private String sets;
    private int experience;
    private int isActive;

    private Integer exerciseId;
    private Integer scheduleID;

}
