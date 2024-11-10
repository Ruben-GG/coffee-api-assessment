package com.assessment.coffeeassessment.model;

import lombok.Data;

import java.util.Map;

@Data
public class Product {
    private String drinkName;
    private Map<String, Double> prices;
}
