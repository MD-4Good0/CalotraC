package com.ai.calorieTrackerApp.repositories;

import com.ai.calorieTrackerApp.models.FoodCalorie;
import com.ai.calorieTrackerApp.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodCalorieRepo extends JpaRepository<FoodCalorie, Long> {
    List<FoodCalorie> findByUserAndDate(UserModel user, LocalDate date);

    List<FoodCalorie> findByUserAndDateAndItemType(UserModel user, LocalDate date, String itemType);


    //method to find user by emailId and password returns Optional of User
    }

