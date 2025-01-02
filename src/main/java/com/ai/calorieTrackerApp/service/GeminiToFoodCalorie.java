package com.ai.calorieTrackerApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class GeminiToFoodCalorie {

    public void saveToFoodCalorie() throws Exception  {
        String json = "[{ \\\"name\\\": \\\"Rice\\\", \\\"quantity\\\": 300, \\\"calories\\\": 210, \\\"protein\\\": 4.5, \\\"carbohydrate\\\": 45, \\\"fat\\\": 0.5 }, { \\\"name\\\": \\\"Mutton Curry\\\", \\\"quantity\\\": 200, \\\"calories\\\": 250, \\\"protein\\\": 25, \\\"carbohydrate\\\": 5, \\\"fat\\\": 15 }, { \\\"name\\\": \\\"Fish Fry\\\", \\\"quantity\\\": 100, \\\"calories\\\": 200, \\\"protein\\\": 15, \\\"carbohydrate\\\": 10, \\\"fat\\\": 15 }, { \\\"name\\\": \\\"Pickle\\\", \\\"quantity\\\": 20, \\\"calories\\\": 10, \\\"protein\\\": 0.5, \\\"carbohydrate\\\": 2, \\\"fat\\\": 0.5 }]";

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object> > map = mapper.readValue(json, List.class);
        for (Map<String,Object> l:map){
        for (Map.Entry<String, Object> entry : l.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }}
    }
}
