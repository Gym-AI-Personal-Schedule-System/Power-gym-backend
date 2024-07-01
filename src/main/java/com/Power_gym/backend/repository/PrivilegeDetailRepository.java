package com.Power_gym.backend.repository;

import com.Power_gym.backend.models.PrivilegeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeDetailRepository extends JpaRepository<PrivilegeDetails,Integer> {
}
