package com.assessment.coffeeassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
    private String user;
    private double amount;
}