package com.Power_gym.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleID;

    @Column(name = "schedule_name")
    private String scheduleName;

    @Column(name = "create time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private Integer isActive;

    @Column(name = "workout_no")
    private Integer workoutNo;

}
