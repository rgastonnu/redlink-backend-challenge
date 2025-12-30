package com.redlink.challenge.model;

import java.time.LocalDateTime;

public class LoanView {

    private final String dni;
    private final LocalDateTime viewedAt;

    public LoanView(String dni, LocalDateTime viewedAt) {
        this.dni = dni;
        this.viewedAt = viewedAt;
    }

    public String getDni() {
        return dni;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }
}
