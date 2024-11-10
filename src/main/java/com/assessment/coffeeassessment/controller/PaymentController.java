package com.assessment.coffeeassessment.controller;

import com.assessment.coffeeassessment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payments/v1")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Endpoint to get the total amount paid by each user.
     * @return a map with user as key and total paid amount as value
     */
    @GetMapping("/amount-paid")
    public Map<String, Double> getAmountPaidByAllUsers() {
        return paymentService.getAmountPaidByAllUsers();
    }

    /**
     * Endpoint to get the total amount owed by each user.
     * @return a map with user as key and amount owed as value
     */
    @GetMapping("/amount-owed")
    public Map<String, Double> getAmountOwedByAllUsers() {
        return paymentService.getAmountOwedByAllUsers();
    }
}