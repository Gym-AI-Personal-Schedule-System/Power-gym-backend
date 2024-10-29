package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    Optional<Schedule> getSchedulesByScheduleID(int id);
    Optional<Schedule> getSchedulesByWorkoutNo(int workoutNo);
}
