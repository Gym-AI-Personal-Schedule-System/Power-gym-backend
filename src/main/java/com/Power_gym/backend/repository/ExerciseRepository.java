package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Integer> {
    Optional<Exercise>getExerciseByExerciseId(int id);
}
