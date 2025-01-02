package com.ai.calorieTrackerApp.controller;

import com.ai.calorieTrackerApp.models.UserDetails;
import com.ai.calorieTrackerApp.service.userDetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserDetailsController {

    private final UserDetailsService service;

    @Autowired
    public UserDetailsController(UserDetailsService service) {
        this.service = service;
    }

    @PostMapping("/createUserDetails/{userId}")
    public UserDetails createUserDetails(@RequestBody UserDetails details, @PathVariable Long userId) {
//        System.out.println(details.getUserName());
        return service.createUserDetails(userId,details);
    }


    @PutMapping("/updateUserDetails/{userId}")
    public UserDetails updateUserDetails(@PathVariable Long userId, @RequestBody UserDetails details) {
//        System.out.println(userId);
        return service.updateUserDetails(userId,details);
    }

    @GetMapping("/getUserDetails/{userId}")
    public UserDetails getUserDetails(@PathVariable Long userId){
            return service.getUserDetails(userId);
    }


}
