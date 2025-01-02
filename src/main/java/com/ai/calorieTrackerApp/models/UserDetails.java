package com.ai.calorieTrackerApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "userDetails")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long detailId;
    @OneToOne
    @JoinColumn(name="userId")
    @JsonIgnore
    private UserModel user;

    private int age;

    private String gender;

    private double height;

    private double weight;

    private String goal;

    private double targetWeight;

    private double BMR;

    private String lifestyle;
}
