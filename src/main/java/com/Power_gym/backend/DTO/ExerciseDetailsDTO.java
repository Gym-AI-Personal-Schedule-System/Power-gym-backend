package com.Power_gym.backend.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDetailsDTO {
    private int exerciseDetailId;
    private int exerciseId;
    private int scheduleID;
    private String sets;
    private int experience;
    private int isActive;
}
