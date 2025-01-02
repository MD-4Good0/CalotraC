package com.ai.calorieTrackerApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "userModel")

@Entity
@Table(name="user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String userName;


    private String emailId;

    private String password;

    private String contactNumber;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private UserDetails details;

    @OneToMany(mappedBy = "user",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<FoodCalorie> fCal;

}
