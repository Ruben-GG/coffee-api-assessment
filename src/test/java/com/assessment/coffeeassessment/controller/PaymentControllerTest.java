package com.assessment.coffeeassessment.controller;

import com.assessment.coffeeassessment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void testGetAmountPaidByAllUsers() throws Exception {
        Map<String, Double> mockResponse = new HashMap<>();

        mockResponse.put("user1", 100.0);
        mockResponse.put("user2", 50.0);

        when(paymentService.getAmountPaidByAllUsers()).thenReturn(mockResponse);

        mockMvc.perform(get("/payments/v1/amount-paid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user1").value(100.0))
                .andExpect(jsonPath("$.user2").value(50.0));
    }

    @Test
    public void testGetAmountOwedByAllUsers() throws Exception {
        Map<String, Double> mockResponse = new HashMap<>();

        mockResponse.put("user1", 30.0);
        mockResponse.put("user2", 20.0);

        when(paymentService.getAmountOwedByAllUsers()).thenReturn(mockResponse);

        mockMvc.perform(get("/payments/v1/amount-owed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user1").value(30.0))
                .andExpect(jsonPath("$.user2").value(20.0));
    }
}
