package com.assessment.coffeeassessment.model;

import lombok.Data;

@Data
public class Order {
    private int userId;
    private int orderId;
    private int productId;
    private int quantity;
}