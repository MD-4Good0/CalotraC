package com.ai.calorieTrackerApp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextManualDto {
    private String name;
    private double qty;
    private String type;
    private String units;
    private double calories;
    private double protein;
    private double carbohydrates;
    private double fat;
    private LocalDate date;
    private LocalTime time;
    private UserModel user;

}
