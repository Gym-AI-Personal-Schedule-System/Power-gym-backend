package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.UserScheduleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserScheduleDetailsRepository extends JpaRepository<UserScheduleDetails,Integer> {


    @Query("select d from UserScheduleDetails d where d.user.userCode = :userCode and d.isActive = :isActive")
    List<UserScheduleDetails> getAllUserScheduleDetailsByUserCode(String userCode, int isActive);

    @Query("select d from UserScheduleDetails d where d.user.userCode = :userCode and date(d.createTime) = :createDate and d.isActive = :isActive")
    List<UserScheduleDetails> getAllUserScheduleDetailByUserCodeAndDate(String userCode, Date createDate, int isActive);


}

