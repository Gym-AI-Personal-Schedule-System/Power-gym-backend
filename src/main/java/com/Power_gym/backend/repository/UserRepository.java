package com.Power_gym.backend.repository;


import com.Power_gym.backend.DTO.AgeCountDTO;
import com.Power_gym.backend.models.Role;
import com.Power_gym.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByMobileNum(String mobileNumber);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findTopByOrderByIdDesc();

    Optional<User> findByEmail(String email);

    List<User> findAllByIsActiveAndRoles(int isActive,Role role);

    List<User> findAllByIsActive(int isActive);

    @Query("SELECT new com.Power_gym.backend.DTO.AgeCountDTO(u.age, COUNT(u)) " +
            "FROM User u WHERE u.isActive = :isActive AND :role MEMBER OF u.roles " +
            "GROUP BY u.age")
    List<AgeCountDTO> countUsersByAgeAndRole(@Param("isActive") int isActive, @Param("role") Role role);


}
