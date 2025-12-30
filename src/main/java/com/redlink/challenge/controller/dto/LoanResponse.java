package com.redlink.challenge.controller.dto;

import java.math.BigDecimal;

public class LoanResponse {
    private final String dni;
    private final boolean hasCredit;
    private final BigDecimal availableAmount;
    private final String message;

    public LoanResponse(String dni, boolean hasCredit, BigDecimal availableAmount, String message) {
        this.dni = dni;
        this.hasCredit = hasCredit;
        this.availableAmount = availableAmount;
        this.message = message;
    }

    public String getDni() { return dni; }
    public boolean isHasCredit() { return hasCredit; }
    public BigDecimal getAvailableAmount() { return availableAmount; }
    public String getMessage() { return message; }
}
