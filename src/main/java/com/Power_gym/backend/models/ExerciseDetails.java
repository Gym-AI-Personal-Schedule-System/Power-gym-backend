package com.Power_gym.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exercise_details")
public class ExerciseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_detail_id")
    private Integer exerciseDetailId;

    @ManyToOne
    @JoinColumn(name = "exercise_id",referencedColumnName = "exercise_id", insertable = false, updatable = false)
    private Exercise exerciseID;

    @ManyToOne
    @JoinColumn(name = "schedule_id",referencedColumnName = "schedule_id", insertable = false, updatable = false)
    private Schedule scheduleId;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private Integer isActive;
}
