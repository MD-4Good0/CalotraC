package com.ai.calorieTrackerApp.service;

import com.ai.calorieTrackerApp.models.UserModel;
import com.ai.calorieTrackerApp.repositories.UserModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserModelService {

    public  long id=0;

    private final UserModelRepo repo;

    @Autowired
    public UserModelService(UserModelRepo repo){
        this.repo = repo;
    }

//    public void createUser() {
//        userModel user = new userModel();
//        user.setUserId(1L);
//        user.setUserName("Rohit");
//        user.setEmailId("rohit123@example.com");
//        user.setPassword("rohit123");
//        user.setContactNumber("9876543210");
//        repo.save(user);
//    }

    public UserModel createUser(UserModel user) {
        return repo.save(user);
    }
    public UserModel getUser(Long userId){
        return repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserModel getUserByEmail(String emailId){
//        return  repo.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("User not found"));
        Optional<UserModel> userOptional = repo.findByEmailId(emailId);

// Then, we check if the Optional contains a value. If it does, return that value.
// If it doesn't, throw a RuntimeException with a message.
        UserModel user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        id=user.getUserId();

// Finally, return the user.
        return user;
    }
}
