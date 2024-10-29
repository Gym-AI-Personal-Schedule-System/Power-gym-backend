package com.Power_gym.backend.DTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDTO {
    private Integer exerciseId;
    private String exerciseName;
    private String video_url;
    private Integer isActive;



}
