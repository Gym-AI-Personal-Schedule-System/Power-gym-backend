package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.UserSchedule;
import com.Power_gym.backend.models.UserScheduleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule,Integer> {

    @Query("select s from UserSchedule s where s.userScheduleDetails in :detailsList")
    List<UserSchedule> getAllUserScheduleByScheduleDetails(List<UserScheduleDetails> detailsList);

}
