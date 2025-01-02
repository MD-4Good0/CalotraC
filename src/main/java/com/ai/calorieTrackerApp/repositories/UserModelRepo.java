package com.ai.calorieTrackerApp.repositories;

import com.ai.calorieTrackerApp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepo extends JpaRepository<UserModel, Long> {

    boolean existsByEmailId(String emailId);

    Optional<UserModel> findByEmailId(String username);
}