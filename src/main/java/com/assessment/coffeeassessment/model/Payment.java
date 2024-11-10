package com.assessment.coffeeassessment.model;

import lombok.Data;

@Data
public class Payment {
    private int userId;
    private int orderId;
    private double amountPaid;
}