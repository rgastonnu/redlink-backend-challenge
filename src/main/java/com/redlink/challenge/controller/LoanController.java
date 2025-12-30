package com.redlink.challenge.controller;

import com.redlink.challenge.controller.dto.LoanResponse;
import com.redlink.challenge.model.EmployeeCredit;
import com.redlink.challenge.service.EmployeeNotEligibleException;
import com.redlink.challenge.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/loan/{dni}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable String dni) {
        if (dni == null || dni.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            EmployeeCredit credit = loanService.getLoanOfferFor(dni.trim());

            if (!credit.hasCredit()) {
                return ResponseEntity.ok(new LoanResponse(
                        credit.getDni(),
                        false,
                        credit.getAvailableAmount(),
                        "Empleado elegible pero sin crédito disponible"
                ));
            }

            return ResponseEntity.ok(new LoanResponse(
                    credit.getDni(),
                    true,
                    credit.getAvailableAmount(),
                    "Crédito disponible"
            ));
        } catch (EmployeeNotEligibleException ex) {
            return ResponseEntity.status(404).build();
        }
    }
}
