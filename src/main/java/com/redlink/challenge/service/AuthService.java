package com.redlink.challenge.service;

import com.redlink.challenge.repository.EmployeeCreditRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final EmployeeCreditRepository employeeCreditRepository;

    public AuthService(EmployeeCreditRepository employeeCreditRepository) {
        this.employeeCreditRepository = employeeCreditRepository;
    }

    /** Login lógico: si el DNI no existe en la nómina elegible, no puede acceder */
    public void assertEligible(String dni) {
        employeeCreditRepository.findByDni(dni)
                .orElseThrow(() -> new EmployeeNotEligibleException(dni));
    }
}
