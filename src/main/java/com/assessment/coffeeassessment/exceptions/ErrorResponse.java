package com.assessment.coffeeassessment.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the error response in JSON format.
 * This class is used to standardize the structure of error responses.
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
}
