package com.redlink.challenge.service;

import com.redlink.challenge.model.EmployeeCredit;
import com.redlink.challenge.model.LoanView;
import com.redlink.challenge.repository.EmployeeCreditRepository;
import com.redlink.challenge.repository.LoanViewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanService {

    private final EmployeeCreditRepository employeeCreditRepository;
    private final LoanViewRepository loanViewRepository;

    public LoanService(EmployeeCreditRepository employeeCreditRepository,
                       LoanViewRepository loanViewRepository) {
        this.employeeCreditRepository = employeeCreditRepository;
        this.loanViewRepository = loanViewRepository;
    }

    /** Devuelve el registro del empleado (con o sin crédito). Si no existe, 404. */
    public EmployeeCredit getLoanOfferFor(String dni) {
        EmployeeCredit credit = employeeCreditRepository.findByDni(dni)
                .orElseThrow(() -> new EmployeeNotEligibleException(dni));

        // Se considera "visualización" al consultar el préstamo
        loanViewRepository.save(new LoanView(dni, LocalDateTime.now()));

        return credit;
    }
}
