package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.ExerciseDetails;
import com.Power_gym.backend.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDetailsRepository extends JpaRepository<ExerciseDetails,Integer> {
//    List<ExerciseDetails> findAllByIsActiveAndScheduleIdAndExperience(int isActive, Schedule scheduleId, int experience);

    @Query("SELECT e FROM ExerciseDetails e WHERE e.isActive = :isActive AND e.schedule.scheduleID = :scheduleID AND e.experience = :experience")
    List<ExerciseDetails> findAllByIsActiveAndScheduleAndExperience(
            @Param("isActive") int isActive,
            @Param("scheduleID") Integer scheduleID,
            @Param("experience") int experience
    );
}
