package com.redlink.challenge.controller;

import com.redlink.challenge.model.EmployeeCredit;
import com.redlink.challenge.service.EmployeeNotEligibleException;
import com.redlink.challenge.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Test
    void loan_available() throws Exception {
        when(loanService.getLoanOfferFor("12345678"))
                .thenReturn(new EmployeeCredit("12345678", new BigDecimal("100000")));

        mockMvc.perform(get("/api/loan/12345678"))
                .andExpect(status().isOk());
    }

    @Test
    void loan_not_available() throws Exception {
        when(loanService.getLoanOfferFor("00000000"))
                .thenThrow(new EmployeeNotEligibleException("00000000"));

        mockMvc.perform(get("/api/loan/00000000"))
                .andExpect(status().isNotFound());
    }
}
