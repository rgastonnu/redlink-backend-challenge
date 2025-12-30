package com.redlink.challenge.controller.dto;

public class LoginResponse {
    private final String dni;
    private final boolean eligible;

    public LoginResponse(String dni, boolean eligible) {
        this.dni = dni;
        this.eligible = eligible;
    }

    public String getDni() { return dni; }
    public boolean isEligible() { return eligible; }
}
