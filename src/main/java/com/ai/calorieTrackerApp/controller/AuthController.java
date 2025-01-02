package com.ai.calorieTrackerApp.controller;

import com.ai.calorieTrackerApp.models.AuthResponseDto;
import com.ai.calorieTrackerApp.models.UserModel;
import com.ai.calorieTrackerApp.service.authentication.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserModel user){
        var jwtToken=authService.login(user.getEmailId(),user.getPassword());
        var authResponseDto=new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESS);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponseDto);

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserModel user){
        try {
            var jwtToken = authService.signUp(user);
            var authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.USER_CREATED_SUCCESSFULLY);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(authResponseDto);
        }catch(Exception e){
            var authResponseDto = new AuthResponseDto(null, AuthStatus.USER_NOT_CREATED);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(authResponseDto);
        }
    }
}
