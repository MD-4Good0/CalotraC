package com.ai.calorieTrackerApp.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextDto {
    String name;
    double qty;
    String type;
    String units;
}
