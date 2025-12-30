package com.redlink.challenge.service;

public class EmployeeNotEligibleException extends RuntimeException {
    public EmployeeNotEligibleException(String dni) {
        super("DNI not eligible: " + dni);
    }
}
