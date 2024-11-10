package com.assessment.coffeeassessment.service;

import com.assessment.coffeeassessment.exceptions.ProductNotFoundException;
import com.assessment.coffeeassessment.model.Order;
import com.assessment.coffeeassessment.model.Payment;
import com.assessment.coffeeassessment.model.Product;
import com.assessment.coffeeassessment.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private JsonUtils jsonUtils;

    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(jsonUtils);
    }

    @Test
    public void testGetAmountPaidByAllUsers() {

        List<Payment> payments = Arrays.asList(
                new Payment("coach", 30.0),
                new Payment("alice", 15.0),
                new Payment("bob", 25.0)
        );

        when(jsonUtils.loadPayments()).thenReturn(payments);

        Map<String, Double> result = paymentService.getAmountPaidByAllUsers();

        assertEquals(3, result.size());
        assertEquals(30.0, result.get("coach"));
        assertEquals(15.0, result.get("alice"));
        assertEquals(25.0, result.get("bob"));
    }

    @Test
    public void testGetAmountOwedByAllUsers() {

        List<Order> orders = Arrays.asList(
                new Order("coach", "coffee", "medium"),
                new Order("alice", "latte", "large"),
                new Order("bob", "espresso", "small")
        );

        List<Product> products = Arrays.asList(
                new Product("coffee", Map.of("medium", 5.0)),
                new Product("latte", Map.of("large", 6.0)),
                new Product("espresso", Map.of("small", 3.0))
        );

        List<Payment> payments = Arrays.asList(
                new Payment("coach", 1.0),
                new Payment("alice", 6.0),
                new Payment("bob", 0.0)
        );

        when(jsonUtils.loadOrders()).thenReturn(orders);
        when(jsonUtils.loadProducts()).thenReturn(products);
        when(jsonUtils.loadPayments()).thenReturn(payments);

        Map<String, Double> result = paymentService.getAmountOwedByAllUsers();

        assertEquals(2, result.size());
        assertEquals(4.0, result.get("coach"));
        assertEquals(3.0, result.get("bob"));
    }

    @Test
    public void testGetAmountOwedByUser_throwsProductNotFoundException() {

        List<Order> orders = List.of(new Order("coach", "unknown", "medium"));
        List<Product> products = Collections.emptyList();

        when(jsonUtils.loadOrders()).thenReturn(orders);
        when(jsonUtils.loadProducts()).thenReturn(products);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            paymentService.getAmountOwedByAllUsers();
        });

        assertEquals("Product 'unknown' not found", exception.getMessage());
    }
}
