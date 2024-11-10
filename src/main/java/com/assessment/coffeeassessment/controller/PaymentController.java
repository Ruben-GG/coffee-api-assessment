package com.assessment.coffeeassessment.controller;

import com.assessment.coffeeassessment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments/v1")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/amount-paid/{user}")
    public ResponseEntity<Double> getAmountPaid(@PathVariable String user) {
        double amountPaid = paymentService.getAmountPaidForUser(user);
        return ResponseEntity.ok(amountPaid);
    }

    @GetMapping("/amount-owed/{user}")
    public ResponseEntity<Double> getAmountOwed(@PathVariable String user) {
        double amountOwed = paymentService.getAmountOwedForUser(user);
        return ResponseEntity.ok(amountOwed);
    }
}
