package com.ai.calorieTrackerApp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodImg {
    private MultipartFile img;
    private String imgPath;
    private String type;
}
