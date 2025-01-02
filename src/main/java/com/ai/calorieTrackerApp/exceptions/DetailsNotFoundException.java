package com.ai.calorieTrackerApp.exceptions;

public class DetailsNotFoundException extends  RuntimeException {
    public DetailsNotFoundException() {
        super();
    }

    public DetailsNotFoundException(String detailsNotFound) {
        super(detailsNotFound);
    }
}
