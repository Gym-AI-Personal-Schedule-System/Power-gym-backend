package com.Power_gym.backend.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScheduleDTO {
    private Integer scheduleID;
    private String scheduleName;
    private String createTime;
    private Integer isActive;
    private Integer workoutNo;
}
