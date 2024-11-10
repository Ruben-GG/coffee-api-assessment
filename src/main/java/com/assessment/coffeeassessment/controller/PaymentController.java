package com.assessment.coffeeassessment.controller;

import com.assessment.coffeeassessment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/amount-paid/{user}")
    public double getAmountPaid(@PathVariable String user) {
        return paymentService.getAmountPaidForUser(user);
    }

    @GetMapping("/amount-owed/{user}")
    public double getAmountOwed(@PathVariable String user) {
        return paymentService.getAmountOwedForUser(user);
    }
}
