package com.ai.calorieTrackerApp.service;

import com.ai.calorieTrackerApp.controller.GeminiController;
import com.ai.calorieTrackerApp.models.FoodCalorie;
import com.ai.calorieTrackerApp.models.TextManualDto;
import com.ai.calorieTrackerApp.models.UserModel;
import com.ai.calorieTrackerApp.records.GeminiRecord;
import com.ai.calorieTrackerApp.repositories.FoodCalorieRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

@Service
public class GeminiService {
    public static final String GEMINI_PRO = "gemini-pro";
    public static final String GEMINI_1_5_PRO = "gemini-1.5-pro-latest";
    public static final String GEMINI_PRO_VISION = "gemini-pro-vision";

    private final GeminiController geminiInterface;


    @Autowired
    private final FoodCalorieRepo foodcalorierepo;
    private final UserModelService ums;

    public GeminiService(GeminiController geminiInterface, FoodCalorieRepo foodcalorierepo, UserModelService ums) {
        this.geminiInterface = geminiInterface;
        this.foodcalorierepo = foodcalorierepo;
        this.ums = ums;
    }

    public GeminiRecord.ModelList getModels() {
        return geminiInterface.getModels();
    }

    public static void someStaticMethod(GeminiService service) {
        // Now you can access geminiInterface through the service parameter
        GeminiRecord.ModelList models = service.getModels();
    }

    public GeminiRecord.GeminiCountResponse countTokens(String model, GeminiRecord.GeminiRequest request) {
        return geminiInterface.countTokens(model, request);
    }

    public int countTokens(String text) {
        GeminiRecord.GeminiCountResponse response = countTokens(GEMINI_PRO, new GeminiRecord.GeminiRequest(
                List.of(new GeminiRecord.Content(List.of(new GeminiRecord.TextPart(text))))));
        return response.totalTokens();
    }

    public GeminiRecord.GeminiResponse getCompletion(GeminiRecord.GeminiRequest request) {
        return geminiInterface.getCompletion(GEMINI_PRO, request);
    }

    public GeminiRecord.GeminiResponse getCompletionWithModel(String model, GeminiRecord.GeminiRequest request) {
        return geminiInterface.getCompletion(model, request);
    }


    public GeminiRecord.GeminiResponse getCompletionWithImage(GeminiRecord.GeminiRequest request) {
        return geminiInterface.getCompletion(GEMINI_PRO_VISION, request);
    }

    public GeminiRecord.GeminiResponse analyzeImage(GeminiRecord.GeminiRequest request) {
        return geminiInterface.getCompletion(GEMINI_1_5_PRO, request);
    }

    public String getCompletion(String text) {
        GeminiRecord.GeminiResponse response = getCompletion(new GeminiRecord.GeminiRequest(
                List.of(new GeminiRecord.Content(List.of(new GeminiRecord.TextPart(text))))));
        return response.candidates().get(0).content().parts().get(0).text();
    }

//    public String getCompletionWithImage(String text, String imageFileName) throws IOException {
//        geminiRecord.GeminiResponse response = getCompletionWithImage(
//                new geminiRecord.GeminiRequest(List.of(new geminiRecord.Content(List.of(
//                        new geminiRecord.TextPart(text),
//                        new geminiRecord.InlineDataPart(new geminiRecord.InlineData("image/png",
//                                Base64.getEncoder().encodeToString(Files.readAllBytes(
//                                        Path.of("src/main/resources/", imageFileName))))))
//                ))));
//        System.out.println(response);
//        return response.candidates().get(0).content().parts().get(0).text();
//    }

    public String getCompletionWithImage(MultipartFile imgFile) throws IOException {
        byte[] img=imgFile.getBytes();
        String b64Img=Base64.getEncoder().encodeToString(img);
        GeminiRecord.GeminiResponse response = getCompletionWithImage(
                new GeminiRecord.GeminiRequest(List.of(new GeminiRecord.Content(List.of(
                        new GeminiRecord.TextPart("Analyze this image of food as accurately as possible." +
                                "Determine the item name, quantity in grams, calorie in Cal, protein in grams, carbohydrate in grams, fat in grams" +
                                "put all details in form of an array as mentioned below " +
                                "[item name 1, quantity in item 1, calories in item 1, protein in item 1, carbohydrates in item 1, fat in item 1]" +
                                "[item name 2, quantity in item 2, calories in item 2, protein in item 2, carbohydrates in item 2, fat in item 2]" +
                                "Each array should be separated by a new line. Do not put the unit in the array.There might be one or more food items in the image. Do not put anything other than the mentioned properties."),
                        new GeminiRecord.InlineDataPart(new GeminiRecord.InlineData("image/png",
                                b64Img)))
                ))));
//        System.out.println(response);
        return response.candidates().get(0).content().parts().get(0).text();
    }

    public String analyzeImage(String text, String imageFileName) throws IOException {
        GeminiRecord.GeminiResponse response = analyzeImage(
                new GeminiRecord.GeminiRequest(List.of(new GeminiRecord.Content(List.of(
                        new GeminiRecord.TextPart(text),
                        new GeminiRecord.InlineDataPart(new GeminiRecord.InlineData("image/png",
                                Base64.getEncoder().encodeToString(Files.readAllBytes(
                                        Path.of("src/main/resources/", imageFileName))))))
                ))));
        System.out.println(response);
        return response.candidates().get(0).content().parts().get(0).text();
    }

    public boolean saveToFoodCalorie(UserModel user, String itemType, String out, LocalDate date) {
            System.out.println(ums.id);
            System.out.println(user.getUserId());
        if(user.getUserId()==ums.id){
        String[] food = out.trim().split("\n");
        for (String f : food) {
            f = f.substring(1, f.length() - 1);
            FoodCalorie foodcalorie = new FoodCalorie();
            foodcalorie.setDate(date);
            foodcalorie.setTime(LocalTime.now());
            foodcalorie.setUser(user);
            foodcalorie.setItemType(itemType);
            String[] property = f.split(",");
            foodcalorie.setItemName(property[0]);
            foodcalorie.setQuantity(Double.parseDouble(property[1]));
            foodcalorie.setCalories(Double.parseDouble(property[2]));
            foodcalorie.setProtein(Double.parseDouble(property[3]));
            foodcalorie.setCarbohydrates(Double.parseDouble(property[4]));
            foodcalorie.setFat(Double.parseDouble(property[5]));
            foodcalorierepo.save(foodcalorie);
        }
        return true;
        }
        else return false;
    }

    public List<FoodCalorie> getFoodCalorieByDateAndUser(UserModel user, LocalDate date) {
        // Leverage LocalDate for date-only filtering
        if(user.getUserId()==ums.id)
        return foodcalorierepo.findByUserAndDate(user, date);
        else
            return null;
    }


    public List<FoodCalorie> findByUserAndDateAndItemType(UserModel user, LocalDate pDate, String type) {
        if(user.getUserId()==ums.id)
        return foodcalorierepo.findByUserAndDateAndItemType(user, pDate, type);
        else
            return null;
    }

    public FoodCalorie addFoodCalorie(FoodCalorie foodCalorie) {
        // Logic to save the FoodCalorie object
        return foodcalorierepo.save(foodCalorie); // Save the foodCalorie to the database
    }

    public FoodCalorie saveFoodCalorie(FoodCalorie foodCalorie) {
        return foodcalorierepo.save(foodCalorie);
    }

}


