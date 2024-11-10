package com.assessment.coffeeassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private String user;
    private String drink;
    private String size;
}