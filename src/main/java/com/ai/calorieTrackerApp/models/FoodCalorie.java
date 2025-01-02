package com.ai.calorieTrackerApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "foodCalorie")
public class FoodCalorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long foodId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private UserModel user;
    private String itemType;
    private LocalDate date;
    private LocalTime time;

    private String itemName;

    private double quantity;

    private double calories;

    private double protein;

    private double carbohydrates;
    private double fat;

}
