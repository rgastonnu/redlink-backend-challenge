package com.redlink.challenge.model;

import java.math.BigDecimal;

public class EmployeeCredit {

    private final String dni;
    private final BigDecimal availableAmount;

    public EmployeeCredit(String dni, BigDecimal availableAmount) {
        this.dni = dni;
        this.availableAmount = availableAmount;
    }

    public String getDni() {
        return dni;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public boolean hasCredit() {
        return availableAmount != null && availableAmount.compareTo(BigDecimal.ZERO) > 0;
    }
}
