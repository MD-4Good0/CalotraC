package com.ai.calorieTrackerApp.models;

import com.ai.calorieTrackerApp.controller.AuthStatus;

public record AuthResponseDto(String token, AuthStatus authStatus) {

}
