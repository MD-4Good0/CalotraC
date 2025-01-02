package com.ai.calorieTrackerApp.service.authentication;

import com.ai.calorieTrackerApp.models.UserModel;

public interface AuthService {

    String login(String username, String password);

    String signUp(UserModel user);
}
