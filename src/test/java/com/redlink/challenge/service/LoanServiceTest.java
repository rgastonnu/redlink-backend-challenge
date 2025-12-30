package com.redlink.challenge.service;

import com.redlink.challenge.model.EmployeeCredit;
import com.redlink.challenge.repository.EmployeeCreditRepository;
import com.redlink.challenge.repository.LoanViewRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    @Test
    void nonEligibleDni_throwsDomainException() {
        EmployeeCreditRepository credits = new EmployeeCreditRepository();
        LoanViewRepository views = new LoanViewRepository();
        LoanService service = new LoanService(credits, views);

        assertThrows(EmployeeNotEligibleException.class,
                () -> service.getLoanOfferFor("99999999"));
    }

    @Test
    void eligibleDni_returnsAmount_andRegistersView() {
        EmployeeCreditRepository credits = new EmployeeCreditRepository();
        LoanViewRepository views = new LoanViewRepository();
        LoanService service = new LoanService(credits, views);

        credits.save(new EmployeeCredit("30123456", new BigDecimal("150000")));

        EmployeeCredit offer = service.getLoanOfferFor("30123456");

        assertEquals("30123456", offer.getDni());
        assertTrue(offer.hasCredit());
        assertEquals(new BigDecimal("150000"), offer.getAvailableAmount());
        assertEquals(1, views.findByDate(java.time.LocalDate.now()).size());
    }
}
