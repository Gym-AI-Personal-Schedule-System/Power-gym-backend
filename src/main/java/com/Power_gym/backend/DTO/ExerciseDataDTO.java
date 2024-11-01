package com.Power_gym.backend.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDataDTO {
    int exerciseId;
    String exerciseName;
    int experience;
    String sets;
}
