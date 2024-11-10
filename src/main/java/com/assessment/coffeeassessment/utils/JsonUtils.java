package com.assessment.coffeeassessment.utils;

import com.assessment.coffeeassessment.exceptions.InvalidJsonFormatException;
import com.assessment.coffeeassessment.model.Order;
import com.assessment.coffeeassessment.model.Payment;
import com.assessment.coffeeassessment.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@Slf4j
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Order> loadOrders() {
        try {
            return loadData("data/orders.json", new TypeReference<List<Order>>() {
            });
        } catch (IOException e) {
            log.error("Error reading or parsing orders.json", e);
            throw new InvalidJsonFormatException("An error occurred while processing orders data.");
        }
    }

    public List<Payment> loadPayments() {
        try {
            return loadData("data/payments.json", new TypeReference<List<Payment>>() {
            });
        } catch (IOException e) {
            log.error("Error reading or parsing payments.json", e);
            throw new InvalidJsonFormatException("An error occurred while processing payments data.");
        }
    }

    public List<Product> loadProducts() {
        try {
            return loadData("data/products.json", new TypeReference<List<Product>>() {
            });
        } catch (IOException e) {
            log.error("Error reading or parsing products.json", e);
            throw new InvalidJsonFormatException("An error occurred while processing products data.");
        }
    }

    private <T> List<T> loadData(String filePath, TypeReference<List<T>> typeReference) throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource(filePath);
        if (fileUrl == null) {
            log.error("File not found: {}", filePath);
            throw new InvalidJsonFormatException("The specified data file could not be found.");
        }
        File file = new File(fileUrl.getFile());
        try {
            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            log.error("Error processing file: {}", filePath, e);
            throw new InvalidJsonFormatException("An error occurred while processing the data file.");
        }
    }
}