package com.assessment.coffeeassessment.service;

import com.assessment.coffeeassessment.exceptions.InvalidJsonFormatException;
import com.assessment.coffeeassessment.exceptions.ProductNotFoundException;
import com.assessment.coffeeassessment.model.Order;
import com.assessment.coffeeassessment.model.Payment;
import com.assessment.coffeeassessment.model.Product;
import com.assessment.coffeeassessment.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to handle payment-related operations.
 */
@Slf4j
@Service
public class PaymentService {

    private final JsonUtils jsonUtils;

    @Autowired
    public PaymentService(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    /**
     * Get the total amount paid by all users.
     *
     * @return a map with the total amount paid by each user
     * @throws InvalidJsonFormatException if any json data is malformed
     */
    public Map<String, Double> getAmountPaidByAllUsers() {
        log.info("Fetching amount paid by all users");

        // Load payments and group by user
        List<Payment> payments = jsonUtils.loadPayments();

        // Group payments by user and sum the amount for each user
        return payments.stream()
                .collect(Collectors.groupingBy(
                        Payment::getUser,
                        Collectors.summingDouble(Payment::getAmount)
                ));
    }

    /**
     * Get the total amount owed by all users.
     *
     * @return a map with the total amount owed by each user
     * @throws InvalidJsonFormatException if any json data is malformed
     */
    public Map<String, Double> getAmountOwedByAllUsers() {
        log.info("Fetching amount owed by all users");

        // Load orders and products
        List<Order> orders = jsonUtils.loadOrders();
        List<Product> products = jsonUtils.loadProducts();

        // Group orders by user and calculate the total amount owed by each user
        Map<String, Double> amountOwedByUser = new HashMap<>();

        for (Order order : orders) {
            if (order.getUser() == null) continue;

            // Calculate the product price
            double price = getProductPrice(order, products);

            // Accumulate total owed per user
            amountOwedByUser.merge(order.getUser(), price, Double::sum);
        }

        // Calculate total paid for each user
        Map<String, Double> amountPaidByUser = getAmountPaidByAllUsers();

        // Calculate the difference (amount owed - amount paid) for each user
        Map<String, Double> amountOwed = new HashMap<>();
        for (String user : amountOwedByUser.keySet()) {
            double totalOwed = amountOwedByUser.get(user);
            double totalPaid = amountPaidByUser.getOrDefault(user, 0.0);
            amountOwed.put(user, totalOwed - totalPaid);
        }

        return amountOwed;
    }

    /**
     * Get the price of the product in an order.
     *
     * @param order    the order for which the product price is needed
     * @param products the list of all available products
     * @return the price of the product in the order
     * @throws ProductNotFoundException if the product is not found in the product list
     */
    private double getProductPrice(Order order, List<Product> products) {
        log.debug("Fetching price for product: {} with size: {}", order.getDrink(), order.getSize());

        // Find the product and fetch its price
        return products.stream()
                .filter(product -> product.getDrinkName().equals(order.getDrink()))
                .findFirst()
                .map(product -> product.getPrices().get(order.getSize()))
                .orElseThrow(() -> {
                    log.error("Product not found: {}", order.getDrink());
                    return new ProductNotFoundException(order.getDrink());
                });
    }
}