package com.Power_gym.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_schedule")
public class UserSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_schedule_id")
    private Integer userScheduleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_schedule_detail_id", referencedColumnName = "user_schedule_detail_id")
    private UserScheduleDetails userScheduleDetails;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_detail_id", referencedColumnName = "exercise_detail_id")
    private ExerciseDetails exerciseDetails;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private Integer isActive;

    @Override
    public String toString() {
        return "UserSchedule{" +
                "userScheduleID=" + userScheduleID +
                ", userScheduleDetails=" + userScheduleDetails +
                ", exerciseDetails=" + exerciseDetails +
                ", isActive=" + isActive +
                '}';
    }
}
