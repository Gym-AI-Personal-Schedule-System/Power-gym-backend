package com.Power_gym.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Integer exerciseId;

    @Column(name = "exercise_name")
    private String exerciseName;

    @Column(name = "video_url")
    private String video_url;

    @Column(name = "sets")
    private String sets;

    @Column(name = "is_active",columnDefinition = "integer default 1")
    private Integer isActive;



}
