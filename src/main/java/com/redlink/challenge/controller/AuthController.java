package com.redlink.challenge.controller;

import com.redlink.challenge.controller.dto.LoginRequest;
import com.redlink.challenge.controller.dto.LoginResponse;
import com.redlink.challenge.service.AuthService;
import com.redlink.challenge.service.EmployeeNotEligibleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String dni = request.getDni();
        if (dni == null || dni.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            authService.assertEligible(dni.trim());
            return ResponseEntity.ok(new LoginResponse(dni.trim(), true));
        } catch (EmployeeNotEligibleException ex) {
            return ResponseEntity.status(404).body(new LoginResponse(dni.trim(), false));
        }
    }
}
