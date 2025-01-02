package com.ai.calorieTrackerApp.controller;

import com.ai.calorieTrackerApp.records.GeminiRecord;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@HttpExchange("/v1beta/models/")
public interface GeminiController {


    @GetExchange
    GeminiRecord.ModelList getModels();

    @PostExchange("{model}:countTokens")
    GeminiRecord.GeminiCountResponse countTokens(
            @PathVariable String model,
            @RequestBody GeminiRecord.GeminiRequest request);

    @PostExchange("{model}:generateContent")
    GeminiRecord.GeminiResponse getCompletion(
            @PathVariable String model,
            @RequestBody GeminiRecord.GeminiRequest request);




}
