package com.ai.calorieTrackerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Collections;

@SpringBootApplication
public class CalorieTrackerAppApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CalorieTrackerAppApplication.class);
		String port = System.getenv("PORT"); // Railway provides the PORT variable
		app.setDefaultProperties(Collections.singletonMap("server.port", port != null ? port : "8080"));
		app.run(args);
	}
}
