package com.assessment.coffeeassessment.service;

import com.assessment.coffeeassessment.exceptions.OrderNotFoundException;
import com.assessment.coffeeassessment.exceptions.ProductNotFoundException;
import com.assessment.coffeeassessment.model.Order;
import com.assessment.coffeeassessment.model.Payment;
import com.assessment.coffeeassessment.model.Product;
import com.assessment.coffeeassessment.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to handle payment-related operations.
 * <p>
 * This service allows calculating the amount paid and the amount owed by a user
 * based on orders and payments.
 * </p>
 */
@Slf4j
@Service
public class PaymentService {

    private final JsonUtils jsonUtils;

    public PaymentService(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    /**
     * Get the total amount paid by a user.
     *
     * @param user the user whose paid amount is to be calculated
     * @return the total amount paid by the user
     * @throws OrderNotFoundException if no payments are found for the user
     */
    public double getAmountPaidForUser(String user) {
        log.info("Fetching amount paid for user: {}", user);

        // Load payments and check if the user has any payments
        List<Payment> payments = jsonUtils.loadPayments();

        if (payments.stream().noneMatch(payment -> payment.getUser().equals(user))) {
            log.error("No payments found for user: {}", user);
            throw new OrderNotFoundException("No orders found for user: " + user);
        }

        double totalPaid = payments.stream()
                .filter(payment -> payment.getUser().equals(user))
                .mapToDouble(Payment::getAmount)
                .sum();

        if (totalPaid == 0.0) {
            log.error("Total paid is zero for user: {}", user);
            throw new OrderNotFoundException("No orders found for user: " + user);
        }

        return totalPaid;
    }

    /**
     * Get the total amount owed by a user.
     *
     * @param user the user whose owed amount is to be calculated
     * @return the total amount owed by the user
     * @throws OrderNotFoundException   if no orders are found for the user
     * @throws ProductNotFoundException if any ordered product is not found
     */
    public double getAmountOwedForUser(String user) {
        log.info("Fetching amount owed for user: {}", user);

        // Load orders and check if the user has any orders
        List<Order> orders = jsonUtils.loadOrders();
        List<Product> products = jsonUtils.loadProducts();

        if (orders.stream().noneMatch(order -> order.getUser().equals(user))) {
            log.error("No orders found for user: {}", user);
            throw new OrderNotFoundException("No orders found for user: " + user);
        }

        double totalOwed = orders.stream()
                .filter(order -> order.getUser().equals(user))
                .mapToDouble(order -> getProductPrice(order, products))
                .sum();

        if (totalOwed == 0.0) {
            log.error("Total owed is zero for user: {}", user);
            throw new OrderNotFoundException("No orders found for user: " + user);
        }

        // Calculate amount paid
        double totalPaid = getAmountPaidForUser(user);

        return totalOwed - totalPaid;
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