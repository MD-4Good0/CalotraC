package com.ai.calorieTrackerApp.service.userDetails;

import com.ai.calorieTrackerApp.exceptions.DetailsNotFoundException;
import com.ai.calorieTrackerApp.models.UserDetails;
import com.ai.calorieTrackerApp.models.UserModel;
import com.ai.calorieTrackerApp.repositories.UserDetailsRepo;
import com.ai.calorieTrackerApp.repositories.UserModelRepo;
import com.ai.calorieTrackerApp.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private final UserDetailsRepo repo;
    private final UserModelRepo uRepo;

    @Autowired
    private final UserModelService usm;
    public UserDetailsService(UserDetailsRepo repo, UserModelRepo uRepo, UserModelService usm){
        this.repo = repo;
        this.uRepo = uRepo;
        this.usm = usm;
    }

//    public void createUser() {
//        userModel user = new userModel();
//        user.setUserId(1L);
//        user.setUserName("Rohit");
//        user.setEmailId("rohit123@example.com");
//        user.setPassword("rohit123");
//        user.setContactNumber("9876543210");
//        repo.save(user);
//    }


    double bmrCalculator(UserDetails details){
        double bmr=0.0;
        double w= details.getWeight();
        double h= details.getHeight();
        double a= details.getAge();
        if(details.getGender().equals("Male"))
            bmr = (66 + (13.7 * w) + (5 * h) - (6.8*a));
        else
            bmr=(655 + (9.6 *w)+(1.8*h)-(4.7*a));
        return bmr;
    }
    public UserDetails createUserDetails(Long userId, UserDetails details) {
        if(userId==usm.id)
        {
            UserModel user = uRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        details.setUser(user);
        details.setBMR(bmrCalculator(details));
        return repo.save(details);}
        else
            return null;
    }

    public UserDetails updateUserDetails(Long userId, UserDetails details) {
        if(userId==usm.id){
        UserModel user = uRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails existingDetails = repo.findByUser(user).orElseThrow(() -> new RuntimeException("User not found"));
        existingDetails.setWeight(details.getWeight());
        existingDetails.setTargetWeight(details.getTargetWeight());
        existingDetails.setGoal(details.getGoal());
        existingDetails.setLifestyle(details.getLifestyle());
        existingDetails.setHeight(details.getHeight());
        existingDetails.setAge(details.getAge());
        existingDetails.setGender(details.getGender());
        existingDetails.setBMR(bmrCalculator(existingDetails));
//        System.out.println(userId);

        return repo.save(existingDetails);}
        else return null;
    }

    public UserDetails getUserDetails(Long userId){
        if(userId==usm.id){
        UserModel user = uRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            return repo.findByUser(user)
                    .orElseThrow(()->new DetailsNotFoundException
                            ("Details not found"));
//        return repo.findByUser(user).orElseThrow(() -> new DetailsNotFoundException("Details not found"));
        }
        else return null;
    }


}
