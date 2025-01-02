package com.ai.calorieTrackerApp.controller;

import com.ai.calorieTrackerApp.models.UserModel;
import com.ai.calorieTrackerApp.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    private final UserModelService service;

    @Autowired
    public UserController(UserModelService service) {
        this.service = service;
    }

    @PostMapping("/createUser")
    public UserModel createUser(@RequestBody UserModel user) {
        System.out.println(user.getUserName());
        return service.createUser(user);
    }
    @GetMapping("/getUser/{userId}")
    public UserModel getuserDetails(@PathVariable Long userId){
        return service.getUser(userId);
    }

    @GetMapping("/getUserByEmail/{emailId}")
    public UserModel getuserDetailsByEmail(@PathVariable String emailId){
        return service.getUserByEmail(emailId);
    }

}
