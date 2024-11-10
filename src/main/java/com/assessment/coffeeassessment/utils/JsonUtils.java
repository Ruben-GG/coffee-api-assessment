package com.assessment.coffeeassessment.utils;

import com.assessment.coffeeassessment.model.Order;
import com.assessment.coffeeassessment.model.Payment;
import com.assessment.coffeeassessment.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtils {

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Order> readOrders(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
    }

    public List<Payment> readPayments(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Payment.class));
    }

    public List<Product> readProducts(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
    }
}
