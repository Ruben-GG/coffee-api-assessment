package com.assessment.coffeeassessment.model;

import lombok.Data;

@Data
public class Order {
    private String user;
    private String drink;
    private String size;
}