package com.Power_gym.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserScheduleDTO {
    private Integer userScheduleID;
    private UserScheduleDetailDTO userScheduleDetails;
    private ExerciseDetailsDTO exerciseDetails;
    private Integer isActive;


}
