package com.Power_gym.backend.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDataDetailDTO {
 int scheduleID;
 List<ExerciseDataDTO> exerciseDataDTOList;
}
