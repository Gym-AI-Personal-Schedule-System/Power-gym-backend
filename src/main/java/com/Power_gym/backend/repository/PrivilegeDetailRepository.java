package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.Privilege;
import com.Power_gym.backend.models.PrivilegeDetails;
import com.Power_gym.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeDetailRepository extends JpaRepository<PrivilegeDetails,Integer> {
    @Query("select usp.privilege from PrivilegeDetails usp where usp.role = :role and usp.status = 1")
    List<Privilege> findPrivilegeIdsByRole(@Param("role") Role role);
}
