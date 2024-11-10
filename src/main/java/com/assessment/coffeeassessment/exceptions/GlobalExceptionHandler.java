package com.assessment.coffeeassessment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler that returns a structured JSON response
 * containing the error message and HTTP status code.
 *
 * It handles different custom exceptions and maps them to the appropriate HTTP status.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidJsonFormatException and returns a structured JSON response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error response in JSON format
     */
    @ExceptionHandler(InvalidJsonFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJsonFormat(InvalidJsonFormatException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ProductNotFoundException and returns a structured JSON response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error response in JSON format
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles OrderNotFoundException and returns a structured JSON response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error response in JSON format
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles any other generic exceptions and returns a structured JSON response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing the error response in JSON format
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}