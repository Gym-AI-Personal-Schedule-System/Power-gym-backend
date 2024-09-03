package com.Power_gym.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "exercise_details")
public class ExerciseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_detail_id")
    private Integer exerciseDetailId;

    @ManyToOne
    @JoinColumn(name = "exercise_id",referencedColumnName = "exercise_id")
    private Exercise exerciseID;

    @ManyToOne
    @JoinColumn(name = "schedule_id",referencedColumnName = "schedule_id")
    private Schedule scheduleId;

    @Column(name = "sets")
    private String sets;

    @Column(name = "experience")
    private int experience;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private Integer isActive;
}
