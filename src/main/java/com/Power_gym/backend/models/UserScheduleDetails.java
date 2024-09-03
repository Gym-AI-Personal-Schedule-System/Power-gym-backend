package com.Power_gym.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_schedule_details")
public class UserScheduleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_schedule_detail_id")
    private Integer userScheduleDetailID;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "height")
    private Integer height;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "bmi")
    private Double bmi;

    @Column(name = "workout time")
    private Integer workoutTime;

    @Column(name = "fitness goal")
    private String fitnessGoal;


    @Column(name = "create time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private Integer isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_detail_id", referencedColumnName = "exercise_detail_id")
    private ExerciseDetails exerciseDetails;

    @Column(name = "user_id")
    private Integer userId;

}

