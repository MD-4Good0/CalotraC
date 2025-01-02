package com.ai.calorieTrackerApp.controller;

import com.ai.calorieTrackerApp.models.*;
import com.ai.calorieTrackerApp.records.GeminiRecord;
import com.ai.calorieTrackerApp.repositories.UserModelRepo;
import com.ai.calorieTrackerApp.service.GeminiService;
import com.ai.calorieTrackerApp.service.GeminiToFoodCalorie;
import com.ai.calorieTrackerApp.service.UserModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/gemini/ai")
@CrossOrigin(origins = "http://localhost:4200")
public class DetailsController {

    @Autowired
    private GeminiService service;
    private UserModelService userServ;
    private final UserModelRepo repo;
    GeminiToFoodCalorie gfc = new GeminiToFoodCalorie();
    public DetailsController(UserModelRepo repo){
        this.repo = repo;
    }

    @PostMapping("gemini-pro/generateContent")
    public GeminiRecord.GeminiResponse getCompletion(@PathVariable String model, @RequestBody GeminiRecord.GeminiRequest request) {
        return service.getCompletion(request);
    }

    @PostMapping("gemini-pro-vision/generateContent")
    public GeminiRecord.GeminiResponse getCompletionWithImage(@RequestBody GeminiRecord.GeminiRequest request) {
        return service.getCompletionWithImage(request);
    }

    @PostExchange("imgdetail/{userId}/{date}")
    public String imageDetail(@PathVariable long userId, @PathVariable LocalDate date, @ModelAttribute FoodImg obj) throws Exception {
        UserModel user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        String out = service.getCompletionWithImage(obj.getImg());
        service.saveToFoodCalorie(user,obj.getType(),out,date);

//        service2.saveToFoodCalorie();
        System.out.println(out);
        return out;
    }

    @PostExchange("mealDetail/{userId}/{date}")
    public boolean mealDetail(@PathVariable long userId, @PathVariable LocalDate date, @RequestBody TextDto txt)throws Exception {
        UserModel user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        String text="In " + txt.getQty()+txt.getUnits()+" of "+txt.getName()+
                ". Determine the calorie in Cal, protein in grams, carbohydrate in grams, fat in grams" +
                "put all details in form of an array as mentioned below " +
                "[item name 1, quantity in item 1, calories in item 1, protein in item 1, carbohydrates in item 1, fat in item 1]" +
                "[item name 2, quantity in item 2, calories in item 2, protein in item 2, carbohydrates in item 2, fat in item 2]" +
                "Each array should be separated by a new line. Do not put the unit in the array.There might be one or more food items in the image. " +
                "Do not put anything other than the mentioned properties.";
        String out=service.getCompletion(text);
        boolean f=service.saveToFoodCalorie(user,txt.getType(),out, date);

//        service2.saveToFoodCalorie();
        System.out.println(f);
        return f;
    }

    @PostMapping("mealDetailsManual/{userId}/{date}")
    public FoodCalorie addMealDetails(@PathVariable long userId, @PathVariable String date, @RequestBody FoodCalorie foodCalorie) {
        // Parse the date from the path variable
        LocalDate mealDate = LocalDate.parse(date);  // Assuming date format is YYYY-MM-DD

        // Set the provided date to the foodCalorie object
        foodCalorie.setDate(mealDate);

        // Automatically set the time to now if not provided
        if (foodCalorie.getTime() == null) {
            foodCalorie.setTime(LocalTime.now());  // Set current time if not provided
        }

        // Optionally, set the userId to associate the food item with the user
        UserModel user = new UserModel();  // Make sure UserModel is appropriately retrieved
        user.setUserId(userId); // Set userId
        foodCalorie.setUser(user);

        // Call the service to save the foodCalorie
        return service.addFoodCalorie(foodCalorie);
    }

    @GetMapping("check")
    public void getCheck() throws  Exception{
        gfc.saveToFoodCalorie();
    }

    @GetMapping("dailyfooditem/{userId}/{date}")
    public List<FoodCalorie> getFoodCalorieByDateAndUser(@PathVariable long userId, @PathVariable String date){
        UserModel user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        LocalDate pDate = LocalDate.parse(date); // Parse date string to LocalDate
        return service.getFoodCalorieByDateAndUser(user, pDate);
    }

    @GetMapping("dailyfooditemtype/{userId}/{date}/{type}")
    public List<FoodCalorie> getFoodCalorieByDateUserType(@PathVariable long userId, @PathVariable String date, @PathVariable String type){
        UserModel user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        LocalDate pDate = LocalDate.parse(date); // Parse date string to LocalDate
        return service.findByUserAndDateAndItemType(user, pDate, type);
    }
}
