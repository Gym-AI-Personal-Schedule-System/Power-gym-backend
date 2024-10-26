package com.Power_gym.backend.repository;


import com.Power_gym.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByMobileNum(String mobileNumber);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findTopByOrderByIdDesc();

    Optional<User> findByEmail(String email);
}
