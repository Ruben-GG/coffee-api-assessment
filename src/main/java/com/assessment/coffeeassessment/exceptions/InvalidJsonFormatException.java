package com.assessment.coffeeassessment.exceptions;

public class InvalidJsonFormatException extends RuntimeException {
    public InvalidJsonFormatException(String message) {
        super("Invalid JSON format: " + message);
    }
}