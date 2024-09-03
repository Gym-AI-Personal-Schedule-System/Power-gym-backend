package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.ExerciseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseDetailsRepository extends JpaRepository<ExerciseDetails,Integer> {
}
