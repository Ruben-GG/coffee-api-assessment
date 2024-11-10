package com.assessment.coffeeassessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Product {
    @JsonProperty("drink_name")
    private String drinkName;

    private Map<String, Double> prices;
}
