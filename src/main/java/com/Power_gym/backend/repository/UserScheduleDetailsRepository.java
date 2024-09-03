package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.UserScheduleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserScheduleDetailsRepository extends JpaRepository<UserScheduleDetails,Integer> {
}
